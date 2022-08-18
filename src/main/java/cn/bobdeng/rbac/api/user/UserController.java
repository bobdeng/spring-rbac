package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.security.Permission;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private final TenantRepository tenantRepository;

    public UserController(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @PostMapping("/users")
    @Transactional
    @Permission(allows = {"user.create"})
    public void newUser(@RequestBody NewUserForm form, @RequestAttribute("tenant") Tenant tenant) {
        RbacContext.Rbac rbac = getRbac(tenant);
        User user = rbac.addUser(new UserDescription(form.getName()));
        user.setRoles(rbac.roles().stream().filter(role -> form.getRoles().contains(role.identity())).collect(Collectors.toList()));
        user.savePassword(new RawPassword(form.getPassword()));
        rbac.addLoginName(new LoginNameDescription(form.getLoginName(), user.identity()));
    }

    private RbacContext.Rbac getRbac(Tenant tenant) {
        return tenantRepository.rbacContext().asRbac(tenant);
    }

    @GetMapping("/users")
    @Transactional
    public List<User> listUser(@RequestAttribute("tenant") Tenant tenant,
                               @RequestParam(value = "name", required = false) String name) {
        return getRbac(tenant).users().findByName(Optional.ofNullable(name).orElse("") + "%");
    }
}
