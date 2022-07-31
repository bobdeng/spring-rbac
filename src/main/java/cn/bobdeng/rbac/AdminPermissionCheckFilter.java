package cn.bobdeng.rbac;

import cn.bobdeng.rbac.api.AdminToken;
import cn.bobdeng.rbac.api.JwtToken;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class AdminPermissionCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (hasAdminAuth(httpRequest)) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
    }

    private boolean hasAdminAuth(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return false;
        }
        return Arrays.stream(request.getCookies()).filter(
                        cookie -> cookie.getName().equals(Cookies.ADMIN_AUTHORIZATION)
                ).map(cookie -> JwtToken.decode(cookie.getValue(), AdminToken.class))
                .findFirst().isPresent();
    }
}
