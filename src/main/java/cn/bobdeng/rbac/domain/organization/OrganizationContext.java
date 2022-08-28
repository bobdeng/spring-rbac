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

    Employees asEmployees(Tenant tenant);

    Organization.Employees employees(Organization organization);

    interface Organizations extends EntityList<Integer, Organization> {

    }

    interface Employees extends EntityList<Integer, Employee> {

    }
}
