package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.api.pages.AdminLoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserLoginTest extends E2ETest {
    @Autowired
    UserWithTenantFixture userWithTenantFactory;

    @BeforeEach
    public void setup() {
        userWithTenantFactory.init();
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
