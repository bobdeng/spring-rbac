package cn.bobdeng.rbac.domain;

public class Tenants {
    private TenantRepository tenantRepository;

    public Tenants(TenantRepository tenantRepository) {

        this.tenantRepository = tenantRepository;
    }

    public void add(TenantDescription tenantDescription) {
        this.tenantRepository.save(new Tenant(tenantDescription));
    }
}
