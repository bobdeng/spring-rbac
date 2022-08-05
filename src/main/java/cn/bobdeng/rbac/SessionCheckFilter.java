package cn.bobdeng.rbac;

import cn.bobdeng.rbac.api.AdminToken;
import cn.bobdeng.rbac.api.JwtToken;
import cn.bobdeng.rbac.security.Session;
import cn.bobdeng.rbac.security.SessionStore;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

public class SessionCheckFilter implements Filter {
    private final SessionStore sessionStore;

    public SessionCheckFilter(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        checkAdminSession(httpRequest);
        try {
            chain.doFilter(request, response);
        } finally {
            sessionStore.clear();
        }

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
