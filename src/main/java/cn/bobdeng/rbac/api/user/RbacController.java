package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.rbac.RbacContext;

public class RbacController {
    protected TenantRepository tenantRepository;
    protected RbacContext rbacContext;

    public RbacContext.Rbac getRbac(Tenant tenant) {
        return rbacContext.asRbac(tenant);
    }

    public RbacController(TenantRepository tenantRepository, RbacContext rbacContext) {
        this.tenantRepository = tenantRepository;
        this.rbacContext = rbacContext;
    }
}
