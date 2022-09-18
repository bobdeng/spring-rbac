package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.ObjectNotFoundException;
import cn.bobdeng.rbac.security.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {
    @GetMapping("api/1.0/session")
    public Profile getSession(@RequestAttribute(value = "session", required = false) Session session) {
        if (session == null) {
            throw new ObjectNotFoundException();
        }
        if (session.user() != null) {
            return new Profile(session.user());
        }
        return Profile.admin();
    }
}
