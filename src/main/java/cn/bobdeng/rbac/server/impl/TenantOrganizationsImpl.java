package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.tenant.organization.Organization;
import cn.bobdeng.rbac.server.dao.OrganizationDAO;
import cn.bobdeng.rbac.server.dao.OrganizationDO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class TenantOrganizationsImpl implements Tenant.Organizations {
    private final Tenant tenant;
    private final OrganizationDAO organizationDAO;

    public TenantOrganizationsImpl(Tenant tenant, OrganizationDAO organizationDAO) {
        this.tenant = tenant;
        this.organizationDAO = organizationDAO;
    }

    @Override
    public List<Organization> subList(int from, int to) {
        return null;
    }

    @Override
    public Stream<Organization> list() {
        return organizationDAO.findAllByTenantId(tenant.identity()).stream()
                .map(OrganizationDO::toEntity);
    }

    @Override
    public Optional<Organization> findByIdentity(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Organization save(Organization entity) {
        return organizationDAO.save(new OrganizationDO(entity,tenant)).toEntity();
    }

    @Override
    public int size() {
        return 0;
    }
}
