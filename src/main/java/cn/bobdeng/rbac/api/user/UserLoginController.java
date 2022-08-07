package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.LoginForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserLoginController {
    @PostMapping("/user_sessions")
    public void login(@RequestBody LoginForm loginForm) {

    }
}
