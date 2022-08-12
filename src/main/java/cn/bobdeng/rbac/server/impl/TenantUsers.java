package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.server.dao.UserDAO;
import cn.bobdeng.rbac.server.dao.UserDO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TenantUsers implements Tenant.Users {
    private Tenant tenant;
    private UserDAO userDAO;
    private TenantRepository tenantRepository;

    public TenantUsers(Tenant tenant, UserDAO userDAO, TenantRepositoryImpl tenantRepository) {
        this.tenant = tenant;
        this.userDAO = userDAO;
        this.tenantRepository = tenantRepository;
    }

    @Override
    public User save(User user) {
        return userDAO.save(new UserDO(user, tenant)).toUser(tenantRepository);
    }

    @Override
    public List<User> findByName(String name) {
        return userDAO.findAllByTenantIdAndNameLike(tenant.identity(), name)
                .stream()
                .map(userDO -> userDO.toUser(tenantRepository))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByIdentity(Integer integer) {
        return userDAO.findById(integer).map(userDO -> userDO.toUser(tenantRepository));
    }
}
