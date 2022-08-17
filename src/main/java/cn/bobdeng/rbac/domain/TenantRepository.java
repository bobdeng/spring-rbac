package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.archtype.EntityList;
import cn.bobdeng.rbac.domain.config.ConfigurationContext;
import cn.bobdeng.rbac.domain.organization.OrganizationContext;
import cn.bobdeng.rbac.domain.tenant.organization.Organization;


public interface TenantRepository extends EntityList<Integer, Tenant> {
    Tenant save(Tenant tenant);

    Page<Tenant> findByName(String name, int page, int size);

    Tenant.Users users(Tenant tenant);

    Tenant.LoginNames loginNames(Tenant tenant);

    User.UserPassword userPassword(User user);

    User.UserRoles userRoles(User user);

    Organization.Employees employees(Organization organization);

    ConfigurationContext configurationContext();

    OrganizationContext organizationContext();

}
