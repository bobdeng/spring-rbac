package cn.bobdeng.rbac;

import cn.bobdeng.rbac.api.AdminToken;
import cn.bobdeng.rbac.api.JwtToken;
import cn.bobdeng.rbac.api.UserToken;
import cn.bobdeng.rbac.domain.Domain;
import cn.bobdeng.rbac.domain.DomainRepository;
import cn.bobdeng.rbac.security.Session;
import cn.bobdeng.rbac.security.SessionStore;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

public class SessionCheckFilter implements Filter {
    private final SessionStore sessionStore;
    private final DomainRepository domainRepository;

    public SessionCheckFilter(SessionStore sessionStore, DomainRepository domainRepository) {
        this.sessionStore = sessionStore;
        this.domainRepository = domainRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        checkAdminSession(httpRequest);
        checkUserSession(httpRequest);
        readTenant(request, httpRequest);
        try {
            sessionStore.get().ifPresent(session -> httpRequest.setAttribute("session", session));
            chain.doFilter(request, response);
        } finally {
            sessionStore.clear();
        }
    }

    private void checkUserSession(HttpServletRequest httpRequest) {
        sessionStore.set(new Session(new UserToken(1, 1)));
    }

    private void readTenant(ServletRequest request, HttpServletRequest httpRequest) {
        domainRepository.findByDomain(httpRequest.getServerName())
                .map(Domain::tenant).ifPresent(tenant -> request.setAttribute("tenant", tenant));
    }

    private void checkAdminSession(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return;
        }
        Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(Cookies.ADMIN_AUTHORIZATION))
                .map(cookie -> JwtToken.decode(cookie.getValue(), AdminToken.class))
                .findFirst().ifPresent(adminToken -> sessionStore.set(new Session(adminToken)));
    }
}
