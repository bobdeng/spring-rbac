package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.domain.rbac.RawPassword;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.domain.rbac.User;
import cn.bobdeng.rbac.security.Permission;
import cn.bobdeng.rbac.security.PermissionDeniedException;
import cn.bobdeng.rbac.security.Session;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
public class UserPasswordController extends RbacController {

    public UserPasswordController(TenantRepository tenantRepository, RbacContext rbacContext) {
        super(tenantRepository, rbacContext);
    }

    @PatchMapping("/users/{id}/password")
    @Permission(allows = {"user.reset_pass"})
    @Transactional
    public ResetPasswordResult resetPassword(@PathVariable int id,
                                             @RequestAttribute("tenant") Tenant tenant) {
        User user = getRbac(tenant).users().findByIdentity(id).orElseThrow(PermissionDeniedException::new);
        RawPassword randomPassword = RawPassword.random();
        user.savePassword(randomPassword);
        return new ResetPasswordResult(randomPassword.getRawPassword());
    }

    @PutMapping("/password")
    @Transactional
    public void setPassword(@RequestAttribute("session") Session session,
                            @RequestBody SetPasswordForm form,
                            @RequestAttribute("tenant") Tenant tenant) {
        User user = getRbac(tenant).users().findByIdentity(session.userId())
                .filter(it -> it.verifyPassword(form.getPassword()))
                .orElseThrow(() -> new RuntimeException("原密码错误"));
        user.savePassword(new RawPassword(form.getNewPassword()));
    }
}
