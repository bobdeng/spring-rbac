package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.security.Permission;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
public class UserLockController {
    @PostMapping("/users/{id}/lock")
    @Transactional
    @Permission(allows = {"user.lock"})
    public void lockUser(@PathVariable int id, @RequestAttribute("tenant") Tenant tenant) {
        tenant.users().findByIdentity(id).ifPresent(User::lock);
    }
    @DeleteMapping("/users/{id}/lock")
    @Transactional
    @Permission(allows = {"user.lock"})
    public void unlockUser(@PathVariable int id, @RequestAttribute("tenant") Tenant tenant) {
        tenant.users().findByIdentity(id).ifPresent(User::unlock);
    }

}
