package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.security.Admin;
import cn.bobdeng.rbac.security.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
class TestController {
    static Session session;

    @GetMapping("/test_need_admin")
    @Admin
    public String needAdmin(@RequestAttribute(value = "session", required = false) Session session
                            ) {
        TestController.session = session;
        return "hello";
    }
}
