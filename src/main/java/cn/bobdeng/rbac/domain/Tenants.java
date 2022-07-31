package cn.bobdeng.rbac.domain;

public class Tenants {
    private TenantRepository tenantRepository;

    public Tenants(TenantRepository tenantRepository) {

        this.tenantRepository = tenantRepository;
    }

    public Tenant add(TenantDescription tenantDescription) {
        return this.tenantRepository.save(new Tenant(tenantDescription));
    }
}
