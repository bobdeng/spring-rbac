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
        listTenantDomainPage.waitUntil(() -> listTenantDomainPage.hasNoData(), 1000);
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

    @Test
    public void new_tenant_domain_with_name_too_long() {
        ListTenantDomainPage listTenantDomainPage = new ListTenantDomainPage(webDriverHandler);
        listTenantDomainPage.open(tenant);
        listTenantDomainPage.waitUntilNoSpin();
        listTenantDomainPage.clickButton("新 增");
        NewDomainPage newDomainPage = new NewDomainPage(webDriverHandler);
        newDomainPage.inputDomain("www.testtesttesttesttesttesttesttesttesttesttesttesttest.com");
        newDomainPage.clickButton("确 定");
        waitUntil(()->newDomainPage.hasText("域名最长50个字符"),1000);
    }
}
