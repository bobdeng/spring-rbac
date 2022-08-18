package cn.bobdeng.rbac.domain.rbac;

import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.domain.third.ThirdDescription;
import cn.bobdeng.rbac.domain.third.ThirdIdentity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RbacImpl implements RbacContext.Rbac {
    private Tenant.Users users;
    private Tenant.Roles roles;
    private Tenant.LoginNames loginNames;
    private Tenant.ThirdIdentities thirdIdentities;

    public RbacImpl(Tenant.Users users, Tenant.Roles roles, Tenant.LoginNames loginNames, Tenant.ThirdIdentities thirdIdentities) {
        this.users = users;
        this.roles = roles;
        this.loginNames = loginNames;
        this.thirdIdentities = thirdIdentities;
    }

    @Override
    public User addUser(UserDescription userDescription) {
        return users.save(new User(userDescription));
    }

    @Override
    public LoginName addLoginName(LoginNameDescription description) {
        if (loginNames.findByLoginName(description.getName()).isPresent()) {
            throw new DuplicateLoginNameException();
        }
        return loginNames.save(new LoginName(description));
    }

    @Override
    public List<Role> roles() {
        return roles.list().collect(Collectors.toList());
    }

    @Override
    public Role newRole(RoleDescription description) {
        description.validate();
        return roles.save(new Role(description));
    }

    @Override
    public void saveRole(Role role) {
        role.description().validate();
        roles.save(role);
    }

    @Override
    public void deleteRole(Integer roleId) {
        roles.findByIdentity(roleId).ifPresent(roles::delete);
    }

    @Override
    public Optional<Role> getRole(Integer roleId) {
        return roles.findByIdentity(roleId);
    }

    @Override
    public Tenant.Users users() {
        return users;
    }

    @Override
    public Tenant.LoginNames loginNames() {
        return loginNames;
    }

    @Override
    public void newThirdIdentity(ThirdDescription thirdDescription) {
        thirdIdentities.save(new ThirdIdentity(thirdDescription));
    }
    @Override
    public Tenant.ThirdIdentities thirdIdentities() {
        return thirdIdentities;
    }
}
