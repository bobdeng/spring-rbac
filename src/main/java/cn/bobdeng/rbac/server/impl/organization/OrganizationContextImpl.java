package cn.bobdeng.rbac.server.impl.organization;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.organization.OrganizationContext;
import cn.bobdeng.rbac.domain.organization.OrganizationStructureImpl;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.domain.tenant.organization.Organization;
import cn.bobdeng.rbac.server.dao.EmployeeDAO;
import cn.bobdeng.rbac.server.dao.OrganizationDAO;
import cn.bobdeng.rbac.server.dao.UserDAO;
import cn.bobdeng.rbac.server.impl.OrganizationEmployee;
import cn.bobdeng.rbac.server.impl.TenantOrganizationsImpl;
import org.springframework.stereotype.Service;

import javax.inject.Provider;

@Service
public class OrganizationContextImpl implements OrganizationContext {
    private final OrganizationDAO organizationDAO;
    private final EmployeeDAO employeeDAO;
    private final Provider<TenantRepository> tenantRepository;
    private final UserDAO userDAO;
    private final RbacContext rbacContext;

    public OrganizationContextImpl(OrganizationDAO organizationDAO, EmployeeDAO employeeDAO, Provider<TenantRepository> tenantRepository, UserDAO userDAO, RbacContext rbacContext) {
        this.organizationDAO = organizationDAO;
        this.employeeDAO = employeeDAO;
        this.tenantRepository = tenantRepository;
        this.userDAO = userDAO;
        this.rbacContext = rbacContext;
    }

    @Override
    public OrganizationStructure asOrganization(Tenant tenant) {
        return new OrganizationStructureImpl(new TenantOrganizationsImpl(tenant, organizationDAO, this));
    }

    @Override
    public Organization.Employees employees(Organization organization) {
        return new OrganizationEmployee(organization, employeeDAO, tenantRepository.get(), userDAO, rbacContext);
    }
}
