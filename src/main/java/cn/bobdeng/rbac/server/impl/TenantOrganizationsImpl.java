package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.organization.OrganizationContext;
import cn.bobdeng.rbac.domain.organization.Organization;
import cn.bobdeng.rbac.server.dao.OrganizationDAO;
import cn.bobdeng.rbac.server.dao.OrganizationDO;

import java.util.Optional;
import java.util.stream.Stream;

public class TenantOrganizationsImpl implements OrganizationContext.Organizations {
    private final Tenant tenant;
    private final OrganizationDAO organizationDAO;
    private OrganizationContext organizationContext;

    public TenantOrganizationsImpl(Tenant tenant, OrganizationDAO organizationDAO, OrganizationContext organizationContext) {
        this.tenant = tenant;
        this.organizationDAO = organizationDAO;
        this.organizationContext = organizationContext;
    }

    @Override
    public Stream<Organization> list() {
        return organizationDAO.findAllByTenantId(tenant.identity()).stream()
                .map(organizationDO -> organizationDO.toEntity(tenant, organizationContext));
    }

    @Override
    public Optional<Organization> findByIdentity(Integer integer) {
        return organizationDAO.findById(integer).map(organizationDO -> organizationDO.toEntity(tenant, organizationContext));
    }

    @Override
    public Organization save(Organization entity) {
        return organizationDAO.save(new OrganizationDO(entity, tenant)).toEntity(tenant, organizationContext);
    }
}
