package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.rbac.User;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.security.Permission;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
public class UserLockController extends RbacController {

    public UserLockController(TenantRepository tenantRepository, RbacContext rbacContext) {
        super(tenantRepository, rbacContext);
    }

    @PostMapping("/api/1.0/users/{id}/lock")
    @Transactional
    @Permission(allows = {"user.lock"})
    public void lockUser(@PathVariable int id, @RequestAttribute("tenant") Tenant tenant) {
        RbacContext.Rbac rbac = getRbac(tenant);
        rbac.users().findByIdentity(id).ifPresent(User::lock);
    }

    @DeleteMapping("/api/1.0/users/{id}/lock")
    @Transactional
    @Permission(allows = {"user.lock"})
    public void unlockUser(@PathVariable int id, @RequestAttribute("tenant") Tenant tenant) {
        getRbac(tenant).users().findByIdentity(id).ifPresent(User::unlock);
    }

}
