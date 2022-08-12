package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.archtype.LoginNameDescription;
import cn.bobdeng.rbac.domain.RawPassword;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.domain.UserDescription;
import cn.bobdeng.rbac.security.Permission;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {
    @PostMapping("/users")
    @Transactional
    @Permission(allows = {"user.create"})
    public void newUser(@RequestBody NewUserForm form, @RequestAttribute("tenant") Tenant tenant) {
        User user = tenant.addUser(new UserDescription(form.getName()));
        user.setRoles(tenant.roles().stream().filter(role -> form.getRoles().contains(role.identity())).collect(Collectors.toList()));
        user.savePassword(new RawPassword(form.getPassword()));
        tenant.addLoginName(new LoginNameDescription(form.getLoginName(), user.identity()));
    }

    @GetMapping("/users")
    @Transactional
    public List<User> listUser(@RequestAttribute("tenant") Tenant tenant,
                               @RequestParam(value = "name", required = false) String name) {
        return tenant.users().findByName(Optional.ofNullable(name).orElse("") + "%");
    }
}
