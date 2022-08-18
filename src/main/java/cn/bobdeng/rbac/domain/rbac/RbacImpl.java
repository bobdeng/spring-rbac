package cn.bobdeng.rbac.domain.rbac;

import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.domain.third.ThirdDescription;
import cn.bobdeng.rbac.domain.third.ThirdIdentity;

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
        return rbacContext.users(tenant).save(new User(userDescription));
    }

    @Override
    public LoginName addLoginName(LoginNameDescription description) {
        if (rbacContext.loginNames(tenant).findByLoginName(description.getName()).isPresent()) {
            throw new DuplicateLoginNameException();
        }
        return rbacContext.loginNames(tenant).save(new LoginName(description));
    }

    @Override
    public List<Role> listRoles() {
        return rbacContext.roles(tenant).list().collect(Collectors.toList());
    }

    @Override
    public Role newRole(RoleDescription description) {
        description.validate();
        return rbacContext.roles(tenant).save(new Role(description));
    }

    @Override
    public void saveRole(Role role) {
        role.description().validate();
        rbacContext.roles(tenant).save(role);
    }

    @Override
    public void deleteRole(Integer roleId) {
        rbacContext.roles(tenant).findByIdentity(roleId).ifPresent(rbacContext.roles(tenant)::delete);
    }

    @Override
    public Optional<Role> getRole(Integer roleId) {
        return rbacContext.roles(tenant).findByIdentity(roleId);
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
        rbacContext.thirdIdentities(tenant).save(new ThirdIdentity(thirdDescription));
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
