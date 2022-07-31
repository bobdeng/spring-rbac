package cn.bobdeng.rbac.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConsoleController {
    @GetMapping("/rbac/admin/console/home")
    public String home() {
        return "admin/console";
    }
}
