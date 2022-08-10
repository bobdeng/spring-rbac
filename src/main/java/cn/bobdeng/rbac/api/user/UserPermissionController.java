package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.security.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserPermissionController {
    @GetMapping("/permissions")
    public List<String> myPermissions(@RequestAttribute("tenant") Tenant tenant,
                                      @RequestAttribute("session") Session session) {
        return tenant.users().findByIdentity(session.userId())
                .stream()
                .flatMap(user -> user.roles().list())
                .flatMap(role -> role.description().getAllows().stream())
                .distinct().toList();
    }
}
