package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.Cookies;
import cn.bobdeng.rbac.domain.AdminPassword;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Controller
public class AdminLoginController {
    private final AdminPassword.Notifier adminPasswordNotifier;
    private final AdminPassword.Store store;

    public AdminLoginController(AdminPassword.Notifier adminPasswordNotifier, AdminPassword.Store store) {
        this.adminPasswordNotifier = adminPasswordNotifier;
        this.store = store;
    }

    @GetMapping("/rbac/admin/login")
    public String loginPage(Model model) {
        model.addAttribute("loginForm", new AdminLoginForm());
        return "admin/login";
    }

    @PostMapping("/rbac/admin/sessions")
    @Transactional
    public String adminLogin(@ModelAttribute("loginForm") AdminLoginForm adminLoginForm,
                             Model model,
                             HttpServletResponse response) {
        AdminPassword adminPassword = new AdminPassword(adminPasswordNotifier, store);
        if (!adminPassword.verify(adminLoginForm.getPassword())) {
            model.addAttribute("error", "密码已发出");
            return "admin/login";
        }
        String value = new AdminToken().toString();
        Cookie cookie = new Cookie(Cookies.ADMIN_AUTHORIZATION, value);
        response.addCookie(cookie);
        return "/admin/login_success";
    }
}
