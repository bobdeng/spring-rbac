package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.rbac.RbacContext;

public class RbacController {
    protected TenantRepository tenantRepository;

    public RbacContext.Rbac getRbac(Tenant tenant) {
        return tenantRepository.rbacContext().asRbac(tenant);
    }
}
