package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.tenant.organization.Organization;
import cn.bobdeng.rbac.server.dao.EmployeeDAO;
import cn.bobdeng.rbac.server.dao.OrganizationDAO;
import cn.bobdeng.rbac.server.dao.OrganizationDO;

import java.util.Optional;
import java.util.stream.Stream;

public class TenantOrganizationsImpl implements Tenant.Organizations {
    private final Tenant tenant;
    private final OrganizationDAO organizationDAO;
    private final TenantRepository tenantRepository;
    private EmployeeDAO employeeDAO;

    public TenantOrganizationsImpl(Tenant tenant, OrganizationDAO organizationDAO, TenantRepository tenantRepository, EmployeeDAO employeeDAO) {
        this.tenant = tenant;
        this.organizationDAO = organizationDAO;
        this.tenantRepository = tenantRepository;
        this.employeeDAO = employeeDAO;
    }

    @Override
    public Stream<Organization> list() {
        return organizationDAO.findAllByTenantId(tenant.identity()).stream()
                .map(organizationDO -> organizationDO.toEntity(tenantRepository,employeeDAO));
    }

    @Override
    public Optional<Organization> findByIdentity(Integer integer) {
        return organizationDAO.findById(integer).map(organizationDO -> organizationDO.toEntity(tenantRepository, employeeDAO));
    }

    @Override
    public Organization save(Organization entity) {
        return organizationDAO.save(new OrganizationDO(entity, tenant)).toEntity(tenantRepository, employeeDAO);
    }
}
