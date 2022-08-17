package cn.bobdeng.rbac.domain.organization;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.tenant.organization.Organization;
import cn.bobdeng.rbac.domain.tenant.organization.OrganizationDescription;

import java.util.List;

public class OrganizationStructureImpl implements OrganizationContext.OrganizationStructure {
    private final Tenant.Organizations organizations;

    public OrganizationStructureImpl(Tenant.Organizations organizations) {
        this.organizations = organizations;
    }

    @Override
    public List<Organization> all() {
        return organizations.list().toList();
    }

    @Override
    public Tenant.Organizations organizations() {
        return organizations;
    }

    @Override
    public Organization add(OrganizationDescription description) {
        description.validate();
        return organizations.save(new Organization(description));
    }
}
