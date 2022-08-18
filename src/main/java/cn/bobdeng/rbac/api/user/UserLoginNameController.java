package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.ObjectNotFoundException;
import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.security.Permission;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
public class UserLoginNameController {
    private final TenantRepository tenantRepository;

    public UserLoginNameController(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @GetMapping("/users/{id}/login_name")
    @Transactional
    public LoginName getUserLoginName(@RequestAttribute("tenant") Tenant tenant,
                                      @PathVariable Integer id) {
        return tenant.loginNames().findByUser(id).orElse(null);
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
        tenantRepository.rbacContext().asRbac(tenant).addLoginName(new LoginNameDescription(form.getLoginName(), user.identity()));
    }
}
