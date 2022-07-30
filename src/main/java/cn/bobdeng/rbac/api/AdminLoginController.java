package cn.bobdeng.rbac.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;

@Controller
public class AdminLoginController {
    private final AdminPasswordNotifier adminPasswordNotifier;

    public AdminLoginController(AdminPasswordNotifier adminPasswordNotifier) {
        this.adminPasswordNotifier = adminPasswordNotifier;
    }

    @GetMapping("/rbac/admin/login")
    public String loginPage(Model model) {
        model.addAttribute("loginForm", new AdminLoginForm());
        return "admin/login";
    }

    @PostMapping("/rbac/admin/sessions")
    @Transactional
    public String adminLogin(@ModelAttribute("loginForm") AdminLoginForm adminLoginForm,
                             Model model) {
        adminPasswordNotifier.notify("123345");
        model.addAttribute("error", "密码已发出");
        return "admin/login";
    }
}
