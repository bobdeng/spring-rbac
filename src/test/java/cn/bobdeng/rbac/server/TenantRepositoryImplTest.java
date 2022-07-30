package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantDescription;
import cn.bobdeng.rbac.domain.TenantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TenantRepositoryImplTest {
    @Autowired
    TenantRepository tenantRepository;

    @Test
    public void save() {
        Tenant tenant = new Tenant(new TenantDescription("bob"));
        tenantRepository.save(tenant);
        assertNotNull(tenant.identity());
    }

    @Test
    public void findByName() {
        for (int i = 0; i < 20; i++) {
            Tenant tenant = new Tenant(new TenantDescription("bob" + i));
            tenantRepository.save(tenant);
        }
        List<Tenant> tenants = tenantRepository.findByName("bob", 10, 20).collect(Collectors.toList());
        assertEquals(10, tenants.size());
        assertEquals("bob10", tenants.get(0).getDescription().getName());
    }
}