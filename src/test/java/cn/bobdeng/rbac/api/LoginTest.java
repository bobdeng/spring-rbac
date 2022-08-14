package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.api.pages.TestNeedAdminPage;
import cn.bobdeng.rbac.api.pages.AdminLoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends E2ETest {
    @Autowired
    AdminPasswordNotifierImpl adminPasswordNotifier;
    @Autowired
    TestController testController;
    @Autowired
    UserWithTenantFixture userWithTenantFixture;

    @BeforeEach
    public void setup() {
        userWithTenantFixture.init();
        webDriverHandler.removeAllCookies();
        adminPasswordNotifier.clear();
    }

    @Test
    public void should_show_tenant_name() {
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.open();
        waitUntil(() -> adminLoginPage.hasText("租户1"), 1000);
    }

    @Test
    public void should_send_admin_password_when_fail() {
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.open();
        adminLoginPage.loginWith("sysadmin", "123");
        assertEquals("密码已经发出", adminLoginPage.error());
        assertNotNull(adminPasswordNotifier.getPassword());
        assertTrue(new BCryptPasswordEncoder().matches(adminPasswordNotifier.getPassword(), adminPasswordNotifier.getEncodedPassword()));
    }

    @Test
    public void should_success_when_match() throws InterruptedException {
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.open();
        adminPasswordNotifier.setEncodedPassword(new BCryptPasswordEncoder().encode("123456"));
        adminLoginPage.loginWith("sysadmin", "123456");
        Cookie authorization = webDriverHandler.getCookie("AdminAuthorization");
        assertNotNull(authorization);
        adminLoginPage.waitUntil(() -> adminLoginPage.hasText("租户管理"), 1000);
    }

    @Test
    public void not_allowed_other_when_not_login() {
        TestNeedAdminPage adminConsolePage = new TestNeedAdminPage(webDriverHandler);
        adminConsolePage.open();
        webDriverHandler.removeAllCookies();
        adminConsolePage.open();
        System.out.println(adminConsolePage.content());
        assertTrue(adminConsolePage.content().contains("无权限"));
    }

    @Test
    public void should_visit_when_logon_and_no_page() {
        adminLogin();
        TestNeedAdminPage adminConsolePage = new TestNeedAdminPage(webDriverHandler);
        adminConsolePage.open();
        assertTrue(adminConsolePage.content().contains("hello"));
        assertNotNull(testController.session);
    }

    @Test
    public void 当没有配置微信不显示微信登录按钮() {
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.open();
        adminLoginPage.waitUntilNoButtonSpin();
        assertFalse(adminLoginPage.hasText("微信登录"));
    }

}
