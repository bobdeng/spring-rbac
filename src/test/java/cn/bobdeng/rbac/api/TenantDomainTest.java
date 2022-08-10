package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.api.pages.ListTenantDomainPage;
import cn.bobdeng.rbac.api.pages.NewDomainPage;
import cn.bobdeng.rbac.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TenantDomainTest extends E2ETest {
    @Autowired
    TenantRepository tenantRepository;
    @Autowired
    DomainRepository domainRepository;
    private Tenant tenant;

    @BeforeEach
    public void setup() {
        adminLogin();
        clearTable.clearTable("t_rbac_tenant");
        clearTable.clearTable("t_rbac_domain");
        tenant = tenantRepository.save(new Tenant(new TenantDescription("租户1")));
    }

    @Test
    public void should_list_tenant_domain() {
        domainRepository.save(new Domain(new DomainDescription("www.text.com", tenant.identity())));
        ListTenantDomainPage listTenantDomainPage = new ListTenantDomainPage(webDriverHandler);
        listTenantDomainPage.open(tenant);
        listTenantDomainPage.waitUntilNoSpin();
        assertFalse(listTenantDomainPage.hasNoData());
        List<Map<String, String>> domains = listTenantDomainPage.domains();
        assertEquals(1, domains.size());
    }

    @Test
    public void should_delete() {
        domainRepository.save(new Domain(new DomainDescription("www.text.com", tenant.identity())));
        ListTenantDomainPage listTenantDomainPage = new ListTenantDomainPage(webDriverHandler);
        listTenantDomainPage.open(tenant);
        listTenantDomainPage.waitUntilNoSpin();
        listTenantDomainPage.clickButton("删除");
        listTenantDomainPage.clickButton("OK");
        listTenantDomainPage.waitUntilNoSpin();
        assertTrue(listTenantDomainPage.hasNoData());
    }

    @Test
    public void new_tenant_domain() {
        ListTenantDomainPage listTenantDomainPage = new ListTenantDomainPage(webDriverHandler);
        listTenantDomainPage.open(tenant);
        listTenantDomainPage.waitUntilNoSpin();
        listTenantDomainPage.clickButton("新 增");
        NewDomainPage newDomainPage = new NewDomainPage(webDriverHandler);
        newDomainPage.inputDomain("www.test.com");
        newDomainPage.clickButton("确 定");
        newDomainPage.waitUntilNoButtonSpin();
        newDomainPage.waitUntilNoSpin();
        assertFalse(listTenantDomainPage.hasNoData());
        List<Map<String, String>> domains = listTenantDomainPage.domains();
        assertEquals(1, domains.size());
    }
}
