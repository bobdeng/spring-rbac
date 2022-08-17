package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.archtype.HasMany;
import cn.bobdeng.rbac.archtype.Many;
import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.domain.tenant.organization.Organization;
import cn.bobdeng.rbac.server.dao.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenantRepositoryImpl implements TenantRepository {
    private final TenantDAO tenantDAO;
    private final UserDAO userDAO;
    private final LoginNameDAO loginNameDAO;
    private final PasswordDAO passwordDAO;
    private final DomainDAO domainDAO;
    private final UserRoleDAO userRoleDAO;
    private final RoleDAO roleDAO;
    private final OrganizationDAO organizationDAO;
    private final EmployeeDAO employeeDAO;
    private final ThirdIdentityDAO thirdIdentityDAO;
    private final ParameterDAO parameterDAO;
    private final ExternalParameters externalParameters;
    public TenantRepositoryImpl(TenantDAO tenantDAO,
                                UserDAO userDAO,
                                LoginNameDAO loginNameDAO,
                                PasswordDAO passwordDAO,
                                DomainDAO domainDAO, UserRoleDAO userRoleDAO, RoleDAO roleDAO, OrganizationDAO organizationDAO, EmployeeDAO employeeDAO, ThirdIdentityDAO thirdIdentityDAO, ParameterDAO parameterDAO, ExternalParameters externalParameters) {
        this.tenantDAO = tenantDAO;
        this.userDAO = userDAO;
        this.loginNameDAO = loginNameDAO;
        this.passwordDAO = passwordDAO;
        this.domainDAO = domainDAO;
        this.userRoleDAO = userRoleDAO;
        this.roleDAO = roleDAO;
        this.organizationDAO = organizationDAO;
        this.employeeDAO = employeeDAO;
        this.thirdIdentityDAO = thirdIdentityDAO;
        this.parameterDAO = parameterDAO;
        this.externalParameters = externalParameters;
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
    public Tenant.Users users(Tenant tenant) {
        return new TenantUsers(tenant, userDAO, this);
    }

    @Override
    public Tenant.LoginNames loginNames(Tenant tenant) {
        return new TenantLoginNames(tenant, loginNameDAO, this);
    }

    @Override
    public User.UserPassword userPassword(User user) {
        return new UserPasswordImpl(passwordDAO);
    }

    @Override
    public User.UserRoles userRoles(User user) {
        return new UserRolesImpl(user, userRoleDAO, roleDAO);
    }

    @Override
    public Organization.Employees employees(Organization organization) {
        return new OrganizationEmployee(organization, employeeDAO, this, userDAO);
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
        tenant.setRoles(new TenantRoles(roleDAO, tenant, userRoleDAO));
        tenant.setThirdIdentities(new TenantThirdIdentities(tenant, thirdIdentityDAO));
        tenant.setOrganizations(new TenantOrganizationsImpl(tenant, organizationDAO, this));
        tenant.setParameters(new ParametersImpl(tenant, parameterDAO, externalParameters));
        return tenant;
    }

    private HasMany<Integer, Domain> getDomains(Tenant tenant) {
        return new HasMany<>() {
            @Override
            public Many<Domain> findAll() {
                return getManyDomains(tenant);
            }
        };
    }

    private Many<Domain> getManyDomains(Tenant tenant) {
        List<Domain> domains = domainDAO.findAllByDescriptionTenantId(tenant.identity());
        return domains::iterator;
    }

}
