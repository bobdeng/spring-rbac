package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.TenantContext;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.Tenants;
import org.springframework.stereotype.Service;

@Service
public class TenantContextImpl implements TenantContext {
    private final TenantRepository tenantRepository;

    public TenantContextImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public Tenants tenants() {
        return new Tenants(tenantRepository);
    }
}
