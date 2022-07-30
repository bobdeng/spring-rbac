package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.server.dao.DomainDAO;
import cn.bobdeng.rbac.server.dao.TenantDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginPageTest {
    @Autowired
    WebDriverHandler webDriverHandler;
    @Autowired
    DomainRepository domainRepository;
    @Autowired
    TenantRepository tenantRepository;
    @Autowired
    DomainDAO domainDAO;
    @Autowired
    TenantDAO tenantDAO;
    private LoginPage loginPage;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        jdbcTemplate.execute("truncate t_rbac_domain");
        jdbcTemplate.execute("truncate t_rbac_tenant");
    }

    @Test
    public void should_show_tenant_name() {
        Tenant tenant = tenantRepository.save(new Tenant(new TenantDescription("租户1")));
        domainRepository.save(new Domain(new DomainDescription("localhost", tenant.getId())));
        assertNotNull(webDriverHandler.WEBDRIVER);
        loginPage = new LoginPage(webDriverHandler);
        loginPage.open();
        assertEquals("登录", loginPage.title());
        assertEquals("租户1", loginPage.tenantName());
    }

    @AfterAll
    public static void tearDown() {
        WebDriverHandler.WEBDRIVER.close();
    }
}
