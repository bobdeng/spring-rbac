package cn.bobdeng.rbac.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController extends BaseController {
    @GetMapping("user/session")
    public Session getSession() {
        return new Session();
    }
}
