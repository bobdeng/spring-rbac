package cn.bobdeng.rbac;

import cn.bobdeng.rbac.api.AdminToken;
import cn.bobdeng.rbac.api.JwtToken;
import cn.bobdeng.rbac.api.UserToken;
import cn.bobdeng.rbac.domain.Domain;
import cn.bobdeng.rbac.domain.DomainRepository;
import cn.bobdeng.rbac.security.Session;
import cn.bobdeng.rbac.security.SessionStore;
import org.jetbrains.annotations.NotNull;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

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
        checkSession(httpRequest);
        readTenant(request, httpRequest);
        try {
            sessionStore.get().ifPresent(session -> httpRequest.setAttribute("session", session));
            chain.doFilter(request, response);
        } finally {
            sessionStore.clear();
        }
    }

    private void checkSession(HttpServletRequest httpRequest) {
        if (httpRequest.getCookies() != null) {
            checkAdminSession(httpRequest);
            checkUserSession(httpRequest);
        }
    }

    private void checkUserSession(HttpServletRequest request) {
        getCookie(request, Cookies.AUTHORIZATION)
                .map(cookie -> JwtToken.decode(cookie.getValue(), UserToken.class))
                .ifPresent(userToken -> sessionStore.set(new Session(userToken)));
    }

    @NotNull
    private Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst();
    }

    private void readTenant(ServletRequest request, HttpServletRequest httpRequest) {
        domainRepository.findByDomain(httpRequest.getServerName())
                .map(Domain::tenant).ifPresent(tenant -> request.setAttribute("tenant", tenant));
    }

    private void checkAdminSession(HttpServletRequest request) {
        getCookie(request, Cookies.ADMIN_AUTHORIZATION)
                .map(cookie -> JwtToken.decode(cookie.getValue(), AdminToken.class))
                .ifPresent(adminToken -> sessionStore.set(new Session(adminToken)));
    }
}
