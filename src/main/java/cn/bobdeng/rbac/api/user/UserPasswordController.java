package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.security.Permission;
import cn.bobdeng.rbac.security.PermissionDeniedException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class UserPasswordController {
    @PatchMapping("/users/{id}/password")
    @Permission(allows = {"user.reset_pass"})
    @Transactional
    public ResetPasswordResult resetPassword(@PathVariable int id,
                                             @RequestAttribute("tenant") Tenant tenant) {
        User user = tenant.users().findByIdentity(id).orElseThrow(PermissionDeniedException::new);
        String newPassword = RandomStringUtils.randomAlphanumeric(10);
        user.resetPassword(newPassword);
        return new ResetPasswordResult(newPassword);
    }
}
