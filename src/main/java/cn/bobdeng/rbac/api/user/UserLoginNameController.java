package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.ObjectNotFoundException;
import cn.bobdeng.rbac.domain.LoginName;
import cn.bobdeng.rbac.domain.LoginNameDescription;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.security.Permission;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
public class UserLoginNameController {
    @GetMapping("/users/{id}/login_name")
    @Transactional
    public LoginName getUserLoginName(@RequestAttribute("tenant") Tenant tenant,
                                      @PathVariable Integer id) {
        return tenant.users().findByIdentity(id)
                .flatMap(user -> user.loginName().get())
                .orElse(null);
    }

    @DeleteMapping("/login_names/{id}")
    @Transactional
    @Permission(allows = {"user.login_name"})
    public void deleteLoginName(@PathVariable Integer id, @RequestAttribute("tenant") Tenant tenant) {
        tenant.loginNames().delete(id);
    }

    @PostMapping("/login_names")
    @Transactional
    @Permission(allows = {"user.login_name"})
    public void newLoginName(@RequestBody NewLoginNameForm form, @RequestAttribute("tenant") Tenant tenant) {
        User user = tenant.users().findByIdentity(form.getUserId()).orElseThrow(ObjectNotFoundException::new);
        tenant.addLoginName(new LoginNameDescription(form.getLoginName(), user));
    }
}
