package cn.bobdeng.rbac.server.impl.rbac;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.config.ConfigurationContext;
import cn.bobdeng.rbac.domain.rbac.User;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.domain.rbac.RbacImpl;
import cn.bobdeng.rbac.server.dao.*;
import org.springframework.stereotype.Service;

import javax.inject.Provider;

@Service
public class RbacContextImpl implements RbacContext {
    private final UserDAO userDAO;
    private final Provider<TenantRepository> tenantRepositoryProvider;
    private final RoleDAO roleDAO;
    private final UserRoleDAO userRoleDAO;
    private final LoginNameDAO loginNameDAO;
    private final ThirdIdentityDAO thirdIdentityDAO;
    private final PasswordDAO passwordDAO;
    private final ConfigurationContext configurationContext;

    public RbacContextImpl(UserDAO userDAO,
                           Provider<TenantRepository> tenantRepositoryProvider,
                           RoleDAO roleDAO,
                           UserRoleDAO userRoleDAO,
                           LoginNameDAO loginNameDAO,
                           ThirdIdentityDAO thirdIdentityDAO, PasswordDAO passwordDAO, ConfigurationContext configurationContext) {
        this.userDAO = userDAO;
        this.tenantRepositoryProvider = tenantRepositoryProvider;
        this.roleDAO = roleDAO;
        this.userRoleDAO = userRoleDAO;
        this.loginNameDAO = loginNameDAO;
        this.thirdIdentityDAO = thirdIdentityDAO;
        this.passwordDAO = passwordDAO;
        this.configurationContext = configurationContext;
    }

    @Override
    public User.UserRoles userRoles(User user) {
        return new UserRolesImpl(user, userRoleDAO, roleDAO);
    }

    @Override
    public Roles roles(Tenant tenant) {
        return new TenantRoles(roleDAO, tenant, userRoleDAO);
    }

    @Override
    public Users users(Tenant tenant) {
        return new TenantUsers(tenant, userDAO, tenantRepositoryProvider.get(), this, configurationContext);
    }

    @Override
    public LoginNames loginNames(Tenant tenant) {
        return new TenantLoginNames(tenant, loginNameDAO, this);
    }

    @Override
    public ThirdIdentities thirdIdentities(Tenant tenant) {
        return new TenantThirdIdentities(tenant, thirdIdentityDAO);
    }

    @Override
    public User.UserPassword userPassword(User user) {
        return new UserPasswordImpl(passwordDAO);
    }

    @Override
    public Rbac asRbac(Tenant tenant) {
        return new RbacImpl(tenant, this);
    }
}
