package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.domain.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class LoginPageTest {
    @Autowired
    WebDriverHandler webDriverHandler;
    @Autowired
    DomainRepository domainRepository;
    @Autowired
    TenantRepository tenantRepository;
    private LoginPage loginPage;

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
