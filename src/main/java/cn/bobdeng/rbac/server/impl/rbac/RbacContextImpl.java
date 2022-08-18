package cn.bobdeng.rbac.server.impl.rbac;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.domain.rbac.RbacImpl;
import cn.bobdeng.rbac.server.dao.*;
import cn.bobdeng.rbac.server.impl.TenantLoginNames;
import cn.bobdeng.rbac.server.impl.TenantRoles;
import cn.bobdeng.rbac.server.impl.TenantThirdIdentities;
import cn.bobdeng.rbac.server.impl.TenantUsers;
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

    public RbacContextImpl(UserDAO userDAO,
                           Provider<TenantRepository> tenantRepositoryProvider,
                           RoleDAO roleDAO,
                           UserRoleDAO userRoleDAO,
                           LoginNameDAO loginNameDAO,
                           ThirdIdentityDAO thirdIdentityDAO) {
        this.userDAO = userDAO;
        this.tenantRepositoryProvider = tenantRepositoryProvider;
        this.roleDAO = roleDAO;
        this.userRoleDAO = userRoleDAO;
        this.loginNameDAO = loginNameDAO;
        this.thirdIdentityDAO = thirdIdentityDAO;
    }

    @Override
    public Rbac asRbac(Tenant tenant) {
        Users users=new TenantUsers(tenant,userDAO,tenantRepositoryProvider.get());
        Roles roles=new TenantRoles(roleDAO,tenant,userRoleDAO);
        LoginNames loginNames=new TenantLoginNames(tenant,loginNameDAO,tenantRepositoryProvider.get());
        ThirdIdentities thirdIdentities=new TenantThirdIdentities(tenant,thirdIdentityDAO);
        return new RbacImpl(users,roles,loginNames,thirdIdentities);
    }
}
