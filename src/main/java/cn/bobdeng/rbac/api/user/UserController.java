package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.domain.*;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    @PostMapping("/users")
    @Transactional
    public void newUser(@RequestBody NewUserForm form, @RequestAttribute("tenant") Tenant tenant) {
        User user = tenant.addUser(new UserDescription(form.getName()));
        user.setRoles(tenant.roles().stream().filter(role -> form.getRoles().contains(role.identity())).collect(Collectors.toList()));
        user.savePassword(new RawPassword(form.getPassword()));
        tenant.addLoginName(new LoginNameDescription(form.getLoginName(), user));
    }

    @GetMapping("/users")
    @Transactional
    public List<User> listUser(@RequestAttribute("tenant") Tenant tenant) {
        return tenant.users().findByName("%");
    }
}
