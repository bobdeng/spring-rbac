package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.Tenant;
import cn.bobdeng.rbac.User;

import java.util.List;
import java.util.Optional;
public class TenantUsers implements Tenant.Users {
    private Tenant tenant;

    public TenantUsers(Tenant tenant) {
        this.tenant = tenant;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<User> findByName(String name) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
