package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.Tenant;
import cn.bobdeng.rbac.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class TenantUsers implements Tenant.Users {
    private Tenant tenant;
    private UserDAO userDAO;

    public TenantUsers(Tenant tenant, UserDAO userDAO) {
        this.tenant = tenant;
        this.userDAO = userDAO;
    }

    @Override
    public User save(User user) {
        return userDAO.save(new UserDO(user, tenant)).toUser();
    }

    @Override
    public List<User> findByName(String name) {
        return null;
    }

    @Override
    public Optional<User> findByAccount(String account) {
        return Optional.empty();
    }

    @Override
    public List<User> subList(int from, int to) {
        return null;
    }

    @Override
    public Stream<User> list() {
        return null;
    }

    @Override
    public Optional<User> findByIdentity(Integer integer) {
        return Optional.empty();
    }

    @Override
    public int size() {
        return 0;
    }
}
