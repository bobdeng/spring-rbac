package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.archtype.EntityList;
import cn.bobdeng.rbac.domain.config.ConfigurationContext;
import cn.bobdeng.rbac.domain.organization.OrganizationContext;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.domain.organization.Organization;


public interface TenantRepository extends EntityList<Integer, Tenant> {
    Tenant save(Tenant tenant);

    Page<Tenant> findByName(String name, int page, int size);

    Organization.Employees employees(Organization organization);

    ConfigurationContext configurationContext();

    OrganizationContext organizationContext();

    RbacContext rbacContext();

}
