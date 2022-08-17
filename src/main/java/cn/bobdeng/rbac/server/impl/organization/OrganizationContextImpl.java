package cn.bobdeng.rbac.server.impl.organization;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.organization.OrganizationContext;
import cn.bobdeng.rbac.domain.organization.OrganizationStructureImpl;
import cn.bobdeng.rbac.server.dao.EmployeeDAO;
import cn.bobdeng.rbac.server.dao.OrganizationDAO;
import cn.bobdeng.rbac.server.impl.TenantOrganizationsImpl;
import org.springframework.stereotype.Service;

import javax.inject.Provider;

@Service
public class OrganizationContextImpl implements OrganizationContext {
    private final OrganizationDAO organizationDAO;
    private final EmployeeDAO employeeDAO;
    private final Provider<TenantRepository> tenantRepository;

    public OrganizationContextImpl(OrganizationDAO organizationDAO, EmployeeDAO employeeDAO, Provider<TenantRepository> tenantRepository) {
        this.organizationDAO = organizationDAO;
        this.employeeDAO = employeeDAO;
        this.tenantRepository = tenantRepository;
    }

    @Override
    public OrganizationStructure asOrganization(Tenant tenant) {
        return new OrganizationStructureImpl(new TenantOrganizationsImpl(tenant, organizationDAO, tenantRepository.get(), employeeDAO));
    }
}
