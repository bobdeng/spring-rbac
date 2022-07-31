package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.server.dao.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TenantRepositoryImpl implements TenantRepository {
    private final TenantDAO tenantDAO;
    private final UserDAO userDAO;
    private final LoginNameDAO loginNameDAO;
    private final PasswordDAO passwordDAO;
    private final DomainDAO domainDAO;

    public TenantRepositoryImpl(TenantDAO tenantDAO,
                                UserDAO userDAO,
                                LoginNameDAO loginNameDAO,
                                PasswordDAO passwordDAO,
                                DomainDAO domainDAO) {
        this.tenantDAO = tenantDAO;
        this.userDAO = userDAO;
        this.loginNameDAO = loginNameDAO;
        this.passwordDAO = passwordDAO;
        this.domainDAO = domainDAO;
    }

    @Override
    public Tenant save(Tenant tenant) {
        tenant = tenantDAO.save(tenant);
        return injectDependencies(tenant);
    }

    @Override
    public Page<Tenant> findByName(String name, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        org.springframework.data.domain.Page<Tenant> result = tenantDAO.findByDescriptionNameContaining(Optional.ofNullable(name).orElse(""), pageable);
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
        tenant.setDomains(getDomains(tenant));
        return tenant;
    }

    @NotNull
    private HasMany<Integer, Domain> getDomains(Tenant tenant) {
        return new HasMany<Integer, Domain>() {
            @Override
            public Many<Domain> findAll() {
                return getManyDomains(tenant);
            }

            @Override
            public Optional<Domain> findByIdentity(Integer identifier) {
                return Optional.empty();
            }
        };
    }

    @NotNull
    private Many<Domain> getManyDomains(Tenant tenant) {
        List<Domain> domains = domainDAO.findAllByDescriptionTenantId(tenant.identity()).collect(Collectors.toList());
        return new Many<Domain>() {
            @Override
            public int size() {
                return domains.size();
            }

            @Override
            public Many<Domain> subCollection(int from, int to) {
                return null;
            }

            @NotNull
            @Override
            public Iterator<Domain> iterator() {
                return domains.iterator();
            }
        };
    }

    @Override
    public int size() {
        return 0;
    }
}
