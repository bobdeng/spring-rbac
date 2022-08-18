package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.server.dao.UserDAO;
import cn.bobdeng.rbac.server.dao.UserDO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TenantUsers implements RbacContext.Users {
    private Tenant tenant;
    private UserDAO userDAO;
    private TenantRepository tenantRepository;
    private RbacContext rbacContext;

    public TenantUsers(Tenant tenant, UserDAO userDAO, TenantRepository tenantRepository, RbacContext rbacContext) {
        this.tenant = tenant;
        this.userDAO = userDAO;
        this.tenantRepository = tenantRepository;
        this.rbacContext = rbacContext;
    }

    @Override
    public User save(User user) {
        return userDAO.save(new UserDO(user, tenant)).toUser(tenantRepository, rbacContext);
    }

    @Override
    public List<User> findByName(String name) {
        return userDAO.findAllByTenantIdAndNameLike(tenant.identity(), name)
                .stream()
                .map(userDO -> userDO.toUser(tenantRepository, rbacContext))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByIdentity(Integer integer) {
        return userDAO.findById(integer).map(userDO -> userDO.toUser(tenantRepository, rbacContext));
    }
}
