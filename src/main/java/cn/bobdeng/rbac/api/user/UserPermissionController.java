package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.security.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserPermissionController extends RbacController {

    public UserPermissionController(TenantRepository tenantRepository, RbacContext rbacContext) {
        super(tenantRepository, rbacContext);
    }

    @GetMapping("/api/1.0/permissions")
    public List<String> myPermissions(@RequestAttribute("tenant") Tenant tenant,
                                      @RequestAttribute("session") Session session) {
        return getRbac(tenant).users().findByIdentity(session.userId())
                .stream()
                .flatMap(user -> user.userRoles().list())
                .flatMap(role -> role.description().getAllows().stream())
                .distinct().toList();
    }
}
