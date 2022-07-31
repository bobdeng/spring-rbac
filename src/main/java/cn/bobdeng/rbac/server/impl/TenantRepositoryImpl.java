package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.Page;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.server.dao.LoginNameDAO;
import cn.bobdeng.rbac.server.dao.PasswordDAO;
import cn.bobdeng.rbac.server.dao.TenantDAO;
import cn.bobdeng.rbac.server.dao.UserDAO;
import org.springframework.data.domain.PageRequest;
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
    public List<Tenant> findByName(String name, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        List<Tenant> byDescriptionNameContaining = tenantDAO.findByDescriptionNameContaining(name, pageable).getContent();
        System.out.println(pageable);
        return byDescriptionNameContaining;
    }

    @Override
    public Page<Tenant> findByName1(String name, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        org.springframework.data.domain.Page<Tenant> result = tenantDAO.findByDescriptionNameContaining(name, pageable);
        return new Page<>(result.getContent(), result.getTotalPages(), result.getTotalElements(), result.getNumber());
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
