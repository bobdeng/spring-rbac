package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.E2ETest;
import cn.bobdeng.rbac.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTest extends E2ETest {
    @Autowired
    DomainRepository domainRepository;
    @Autowired
    TenantRepository tenantRepository;
    private Tenant tenant;

    @BeforeEach
    public void setup() {
        tenant = tenantRepository.save(new Tenant(new TenantDescription("租户名称1")));
        domainRepository.save(new Domain(new DomainDescription("localhost", tenant.identity())));
    }

    @Test
    public void should_show_tenant_name() {

    }
}
