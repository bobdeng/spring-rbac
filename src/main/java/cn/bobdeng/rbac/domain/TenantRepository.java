package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.archtype.EntityList;
import cn.bobdeng.rbac.archtype.Page;
import cn.bobdeng.rbac.domain.config.ConfigurationContext;
import cn.bobdeng.rbac.domain.organization.OrganizationContext;
import cn.bobdeng.rbac.domain.rbac.RbacContext;


public interface TenantRepository extends EntityList<Integer, Tenant> {
    Tenant save(Tenant tenant);

    Page<Tenant> findByName(String name, int page, int size);

    ConfigurationContext configurationContext();

    OrganizationContext organizationContext();

    RbacContext rbacContext();

}
