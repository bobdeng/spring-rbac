package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.api.pages.ListTenantPage;
import cn.bobdeng.rbac.api.pages.NewTenantPage;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.server.dao.TenantDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static cn.bobdeng.rbac.api.WebDriverHandler.WEBDRIVER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NewTenantTest extends E2ETest {
    @Autowired
    TenantDAO tenantDAO;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        clearTable.clearTable("t_rbac_tenant");
    }

    @Test
    public void should_save_tenant() {
        adminLogin();
        ListTenantPage listTenantPage = new ListTenantPage(webDriverHandler);
        listTenantPage.open();
        listTenantPage.waitUntilNoSpin();
        listTenantPage.openAdd();
        NewTenantPage newTenantPage = new NewTenantPage(webDriverHandler);
        newTenantPage.inputTenantName("租户1");
        newTenantPage.submit();
        newTenantPage.waitUntilNoButtonSpin();
        listTenantPage.waitUntilNoSpin();
        List<Tenant> tenants = StreamSupport.stream(tenantDAO.findAll().spliterator(), false).collect(Collectors.toList());
        assertEquals(1, tenants.size());
        List<Map<String, String>> tenantsTableValue = listTenantPage.tenants();
        assertEquals(1, tenantsTableValue.size());
        assertEquals("租户1", tenantsTableValue.get(0).get("租户名"));
    }
}
