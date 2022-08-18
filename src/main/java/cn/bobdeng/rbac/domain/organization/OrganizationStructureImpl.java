package cn.bobdeng.rbac.domain.organization;

import java.util.List;

public class OrganizationStructureImpl implements OrganizationContext.OrganizationStructure {
    private final OrganizationContext.Organizations organizations;

    public OrganizationStructureImpl(OrganizationContext.Organizations organizations) {
        this.organizations = organizations;
    }

    @Override
    public List<Organization> all() {
        return organizations.list().toList();
    }

    @Override
    public OrganizationContext.Organizations organizations() {
        return organizations;
    }

    @Override
    public Organization add(OrganizationDescription description) {
        description.validate();
        return organizations.save(new Organization(description));
    }
}
