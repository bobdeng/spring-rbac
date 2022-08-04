package cn.bobdeng.rbac;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoutesController implements ErrorController {
    private static final String PATH = "/admin/*";

    @RequestMapping(value = PATH)
    public String error() {
        return "admin/index";
    }

}