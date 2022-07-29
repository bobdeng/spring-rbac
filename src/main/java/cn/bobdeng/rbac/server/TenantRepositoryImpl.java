package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class TenantRepositoryImpl implements TenantRepository {
    private TenantDAO tenantDAO;
    private UserDAO userDAO;
    private LoginNameDAO loginNameDAO;
    private PasswordDAO passwordDAO;

    public TenantRepositoryImpl(TenantDAO tenantDAO, UserDAO userDAO, LoginNameDAO loginNameDAO, PasswordDAO passwordDAO) {
        this.tenantDAO = tenantDAO;
        this.userDAO = userDAO;
        this.loginNameDAO = loginNameDAO;
        this.passwordDAO = passwordDAO;
    }

    @Override
    public Tenant save(Tenant tenant) {
        tenant = tenantDAO.save(tenant);
        return injectDependencies(tenant);
    }

    @Override
    public Stream<Tenant> findByName(String name, int from, int to) {
        return tenantDAO.findByName(name).skip(from).limit(to - from);
    }

    @Override
    public Optional<Tenant> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<Tenant> findByName(String tenantName) {
        return tenantDAO.findByDescriptionName(tenantName)
                .map(this::injectDependencies)
                .findFirst();
    }

    @Override
    public Tenant.Users users(Tenant tenant) {
        return new TenantUsers(tenant, userDAO, passwordDAO, this);
    }

    @Override
    public Tenant.LoginNames loginNames(Tenant tenant) {
        return new TenantLoginNames(tenant, loginNameDAO, this);
    }

    @Override
    public User.UserPassword userPassword(User user) {
        return new UserPasswordImpl(user, this, passwordDAO);
    }

    @Override
    public List<Tenant> subList(int from, int to) {
        return null;
    }

    @Override
    public Stream<Tenant> list() {
        return null;
    }

    @Override
    public Optional<Tenant> findByIdentity(Integer integer) {
        return tenantDAO.findById(integer)
                .map(this::injectDependencies);
    }

    private Tenant injectDependencies(Tenant tenant) {
        tenant.setUsers(this.users(tenant));
        tenant.setLoginNames(this.loginNames(tenant));
        return tenant;
    }

    @Override
    public int size() {
        return 0;
    }
}
