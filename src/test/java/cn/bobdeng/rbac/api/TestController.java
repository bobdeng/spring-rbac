package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.security.Admin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class TestController {
    @GetMapping("/test_need_admin")
    @Admin
    public String needAdmin() {
        return "hello";
    }
}
