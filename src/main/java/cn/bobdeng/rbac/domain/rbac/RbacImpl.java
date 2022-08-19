package cn.bobdeng.rbac.domain.rbac;

import cn.bobdeng.rbac.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RbacImpl implements RbacContext.Rbac {
    private Tenant tenant;
    private RbacContext rbacContext;

    public RbacImpl(Tenant tenant, RbacContext rbacContext) {
        this.tenant = tenant;
        this.rbacContext = rbacContext;
    }

    public RbacImpl() {
    }

    @Override
    public User addUser(UserDescription userDescription) {
        userDescription.validate();
        return users().save(new User(userDescription));
    }

    @Override
    public LoginName addLoginName(LoginNameDescription description) {
        description.validate();
        if (loginNames().findByLoginName(description.getName()).isPresent()) {
            throw new DuplicateLoginNameException();
        }
        return loginNames().save(new LoginName(description));
    }

    @Override
    public List<Role> listRoles() {
        return roles().list().collect(Collectors.toList());
    }

    @Override
    public Role newRole(RoleDescription description) {
        description.validate();
        return roles().save(new Role(description));
    }

    @Override
    public void saveRole(Role role) {
        role.description().validate();
        roles().save(role);
    }

    @Override
    public void deleteRole(Integer roleId) {
        RbacContext.Roles roles = roles();
        roles.findByIdentity(roleId).ifPresent(roles::delete);
    }

    @Override
    public Optional<Role> getRole(Integer roleId) {
        return roles().findByIdentity(roleId);
    }

    @Override
    public RbacContext.Users users() {
        return rbacContext.users(tenant);
    }

    @Override
    public RbacContext.LoginNames loginNames() {
        return rbacContext.loginNames(tenant);
    }

    @Override
    public void newThirdIdentity(ThirdDescription thirdDescription) {
        thirdIdentities().save(new ThirdIdentity(thirdDescription));
    }

    @Override
    public RbacContext.Roles roles() {
        return rbacContext.roles(tenant);
    }

    @Override
    public RbacContext.ThirdIdentities thirdIdentities() {
        return rbacContext.thirdIdentities(tenant);
    }
}
