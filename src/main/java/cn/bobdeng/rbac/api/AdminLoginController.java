package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.Cookies;
import cn.bobdeng.rbac.domain.rbac.AdminPassword;
import cn.bobdeng.rbac.domain.rbac.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;

@RestController
public class AdminLoginController {
    private final AdminPassword.Notifier adminPasswordNotifier;
    private final AdminPassword.Store store;
    private PasswordEncoder passwordEncoder;

    public AdminLoginController(AdminPassword.Notifier adminPasswordNotifier, AdminPassword.Store store, PasswordEncoder passwordEncoder) {
        this.adminPasswordNotifier = adminPasswordNotifier;
        this.store = store;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/admin_sessions")
    @Transactional
    public void adminLogin(@RequestBody AdminLoginForm adminLoginForm,
                           HttpServletResponse response) throws IOException {
        AdminPassword adminPassword = new AdminPassword(adminPasswordNotifier, store, passwordEncoder);
        if (!adminPassword.verify(adminLoginForm.getPassword())) {
            responseError(response);
            return;
        }
        setLoginCookie(response);
    }

    private void setLoginCookie(HttpServletResponse response) {
        String value = new AdminToken().toString();
        Cookie cookie = new Cookie(Cookies.ADMIN_AUTHORIZATION, value);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private void responseError(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println("密码已经发出");
    }
}
