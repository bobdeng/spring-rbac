package cn.bobdeng.rbac.domain.organization;

import cn.bobdeng.rbac.archtype.EntityList;
import cn.bobdeng.rbac.domain.Tenant;

import java.util.List;

public interface OrganizationContext {
    interface OrganizationStructure {
        List<Organization> all();

        Organizations organizations();

        Organization add(OrganizationDescription description);
    }

    OrganizationStructure asOrganization(Tenant tenant);
    Organization.Employees employees(Organization organization);
    interface Organizations extends EntityList<Integer, Organization> {

    }
}
