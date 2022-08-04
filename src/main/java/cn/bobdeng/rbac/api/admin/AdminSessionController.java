package cn.bobdeng.rbac.api.admin;

import cn.bobdeng.rbac.api.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminSessionController extends BaseController {
    @GetMapping("admin/session")
    public AdminSession getSession() {
        return new AdminSession();
    }
}
