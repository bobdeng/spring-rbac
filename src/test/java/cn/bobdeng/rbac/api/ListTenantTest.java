package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.api.pages.ListTenantPage;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantDescription;
import cn.bobdeng.rbac.domain.TenantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListTenantTest extends E2ETest {
    @Autowired
    TenantRepository tenantRepository;

    @BeforeEach
    public void setup() {
        clearTable("t_rbac_tenant");
        adminLogin();
    }

    @Test
    public void should_show_empty_when_no_tenant() {
        ListTenantPage listTenantPage = new ListTenantPage(webDriverHandler);
        listTenantPage.open();
        List tenants = listTenantPage.tenants();
        assertEquals(0, tenants.size());
        assertEquals(webDriverHandler.getBaseUrl() + "/rbac/admin/console/new_tenant", listTenantPage.newLink());
    }

    @Test
    public void should_show_tenant() {
        Tenant tenant = new Tenant(new TenantDescription("租户1"));
        tenantRepository.save(tenant);
        ListTenantPage listTenantPage = new ListTenantPage(webDriverHandler);
        listTenantPage.open();
        List<Map<String, String>> tenants = listTenantPage.tenants();
        assertEquals(1, tenants.size());
        assertEquals("租户1", tenants.get(0).get("租户名"));
        assertEquals(webDriverHandler.getBaseUrl() + "/rbac/admin/console/tenants/" + tenant.identity() + "/domains", tenants.get(0).get("域名链接"));
        assertEquals("1", listTenantPage.totalElements());
        assertEquals("1/1", listTenantPage.page());
    }

    @Test
    public void should_search_tenant() {
        Tenant tenant = new Tenant(new TenantDescription("张三"));
        Tenant tenant1 = new Tenant(new TenantDescription("李四"));
        tenantRepository.save(tenant);
        tenantRepository.save(tenant1);
        ListTenantPage listTenantPage = new ListTenantPage(webDriverHandler);
        listTenantPage.open();
        listTenantPage.search("张三");
        List<Map<String, String>> tenants = listTenantPage.tenants();
        assertEquals(1, tenants.size());
        assertEquals("张三", tenants.get(0).get("租户名"));
        assertEquals(webDriverHandler.getBaseUrl() + "/rbac/admin/console/tenants/" + tenant.identity() + "/domains", tenants.get(0).get("域名链接"));
        assertEquals("1", listTenantPage.totalElements());
        assertEquals("1/1", listTenantPage.page());
    }

    @Test
    public void should_show_paged_tenant() {

    }
}
