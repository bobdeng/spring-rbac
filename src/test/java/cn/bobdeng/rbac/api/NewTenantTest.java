package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.api.pages.NewTenantPage;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.server.dao.TenantDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewTenantTest extends E2ETest {
    @Autowired
    TenantDAO tenantDAO;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        jdbcTemplate.execute("truncate table t_rbac_tenant");
    }

    @Test
    public void should_save_tenant() {
        adminLogin();
        NewTenantPage newTenantPage = new NewTenantPage(webDriverHandler);
        newTenantPage.open();
        newTenantPage.inputTenantName("租户1");

        newTenantPage.submit();

        List<Tenant> tenants = StreamSupport.stream(tenantDAO.findAll().spliterator(), false).collect(Collectors.toList());
        assertEquals(1, tenants.size());
    }
}
