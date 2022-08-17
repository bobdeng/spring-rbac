package cn.bobdeng.rbac.domain.organization;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.tenant.organization.Organization;
import cn.bobdeng.rbac.domain.tenant.organization.OrganizationDescription;

import java.util.List;

public interface OrganizationContext {
    interface OrganizationStructure {
        List<Organization> all();
        Tenant.Organizations organizations();
        Organization add(OrganizationDescription description);
    }

    OrganizationStructure asOrganization(Tenant tenant);
}
