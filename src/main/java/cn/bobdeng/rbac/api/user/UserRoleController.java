package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.rbac.RawPassword;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.domain.rbac.Role;
import cn.bobdeng.rbac.domain.rbac.User;
import cn.bobdeng.rbac.security.PermissionDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class UserRoleController extends RbacController {

    public UserRoleController(TenantRepository tenantRepository, RbacContext rbacContext) {
        super(tenantRepository, rbacContext);
    }

    @GetMapping("/api/1.0/users/{id}/roles")
    public List<Role> userRoles(@PathVariable int id,
                                @RequestAttribute("tenant") Tenant tenant) {
        User user = getRbac(tenant).users().findByIdentity(id).orElseThrow(PermissionDeniedException::new);
        return user.userRoles().list().toList();
    }

    @PutMapping("/api/1.0/users/{id}/roles")
    @Transactional
    public void setUserRoles(@PathVariable int id,
                             @RequestAttribute("tenant") Tenant tenant,
                             @RequestBody SetRoleForm form) {
        User user = getRbac(tenant).users().findByIdentity(id).orElseThrow(PermissionDeniedException::new);
        List<Role> roles = form.getRoles().stream().map(roleId -> getRbac(tenant).roles().findByIdentity(roleId).orElseThrow()).toList();
        user.setRoles(roles);
    }
}
