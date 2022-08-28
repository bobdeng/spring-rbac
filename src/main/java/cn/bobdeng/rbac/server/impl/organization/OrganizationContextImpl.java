package cn.bobdeng.rbac.server.impl.organization;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.organization.OrganizationContext;
import cn.bobdeng.rbac.domain.organization.OrganizationStructureImpl;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.domain.organization.Organization;
import cn.bobdeng.rbac.server.dao.EmployeeDAO;
import cn.bobdeng.rbac.server.dao.OrganizationDAO;
import org.springframework.stereotype.Service;

@Service
public class OrganizationContextImpl implements OrganizationContext {
    private final OrganizationDAO organizationDAO;
    private final EmployeeDAO employeeDAO;
    private final RbacContext rbacContext;

    public OrganizationContextImpl(OrganizationDAO organizationDAO, EmployeeDAO employeeDAO, RbacContext rbacContext) {
        this.organizationDAO = organizationDAO;
        this.employeeDAO = employeeDAO;
        this.rbacContext = rbacContext;
    }

    @Override
    public OrganizationStructure asOrganization(Tenant tenant) {
        return new OrganizationStructureImpl(new TenantOrganizationsImpl(tenant, organizationDAO, this));
    }

    @Override
    public Employees asEmployees(Tenant tenant) {
        return new EmployeesImpl(employeeDAO,rbacContext,tenant);
    }

    @Override
    public Organization.Employees employees(Organization organization) {
        return new OrganizationEmployee(organization, employeeDAO, rbacContext);
    }
}
