package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.ObjectNotFoundException;
import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.domain.rbac.LoginName;
import cn.bobdeng.rbac.domain.rbac.LoginNameDescription;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.domain.rbac.User;
import cn.bobdeng.rbac.security.Permission;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
public class UserLoginNameController extends RbacController {

    public UserLoginNameController(TenantRepository tenantRepository, RbacContext rbacContext) {
        super(tenantRepository, rbacContext);
    }

    @GetMapping("/api/1.0/users/{id}/login_name")
    @Transactional
    public LoginName getUserLoginName(@RequestAttribute("tenant") Tenant tenant,
                                      @PathVariable Integer id) {
        RbacContext.Rbac rbac = getRbac(tenant);
        return rbac.loginNames().findByUser(id).orElse(null);
    }

    @DeleteMapping("/api/1.0/login_names/{id}")
    @Transactional
    @Permission(allows = {"user.login_name"})
    public void deleteLoginName(@PathVariable Integer id, @RequestAttribute("tenant") Tenant tenant) {
        getRbac(tenant).loginNames().delete(id);
    }

    @PostMapping("/api/1.0/login_names")
    @Transactional
    @Permission(allows = {"user.login_name"})
    public void newLoginName(@RequestBody NewLoginNameForm form, @RequestAttribute("tenant") Tenant tenant) {
        User user = getRbac(tenant).users().findByIdentity(form.getUserId()).orElseThrow(ObjectNotFoundException::new);
        getRbac(tenant).addLoginName(new LoginNameDescription(form.getLoginName(), user.identity()));
    }
}
