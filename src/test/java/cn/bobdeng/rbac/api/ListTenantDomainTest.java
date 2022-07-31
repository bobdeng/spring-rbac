package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.api.pages.ListTenantDomainPage;
import cn.bobdeng.rbac.domain.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListTenantDomainTest extends E2ETest {
    @Autowired
    TenantRepository tenantRepository;
    @Autowired
    DomainRepository domainRepository;

    @BeforeEach
    public void setup() {
        adminLogin();
    }

    @Test
    public void should_list_domain_of_tenant() {
        Tenant tenant = new Tenant(new TenantDescription("租户1"));
        tenantRepository.save(tenant);
        domainRepository.save(new Domain(new DomainDescription("localhost", tenant.identity())));
        ListTenantDomainPage page = new ListTenantDomainPage(webDriverHandler);
        page.open(tenant);
        List<Map<String, String>> domains = page.domains();
        assertEquals(1, domains.size());
        assertEquals("localhost", domains.get(0).get("域名"));
        assertEquals(webDriverHandler.getBaseUrl() + "/rbac/admin/console/tenant/" + tenant.identity() + "/new_domain", page.newDomainLink());
    }
}
