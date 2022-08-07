package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.api.pages.AdminLoginPage;
import cn.bobdeng.rbac.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserLoginTest extends E2ETest {
    @Autowired
    TenantRepository tenantRepository;
    @Autowired
    DomainRepository domainRepository;
    private Tenant tenant;

    @BeforeEach
    public void setup() {
        clearTable("t_rbac_tenant");
        clearTable("t_rbac_user");
        clearTable("t_rbac_domain");
        clearTable("t_rbac_password");
        clearTable("t_rbac_login_name");
        tenant = tenantRepository.save(new Tenant(new TenantDescription("租户1")));
        domainRepository.save(new Domain(new DomainDescription("localhost", tenant.identity())));
        User user = tenant.addUser(new UserDescription("张三"));
        user.savePassword(new RawPassword("123456"));
        tenant.addLoginName(new LoginNameDescription("bobdeng", user));
    }

    @Test
    public void show_tenant_name() {
        AdminLoginPage loginPage = new AdminLoginPage(webDriverHandler);
        loginPage.open();
        assertTrue(loginPage.hasText("租户1"));
    }

    @Test
    public void should_login_success() {
        AdminLoginPage loginPage = new AdminLoginPage(webDriverHandler);
        loginPage.open();
        loginPage.loginWith("bobdeng", "123456");
        loginPage.waitUntilNoButtonSpin();
        assertTrue(WebDriverHandler.WEBDRIVER.getCurrentUrl().endsWith("#/user/console"));
    }
}
