package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.api.pages.TestNeedAdminPage;
import cn.bobdeng.rbac.api.pages.AdminLoginPage;
import cn.bobdeng.rbac.security.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.*;

public class AdminLoginTest extends E2ETest {
    @Autowired
    AdminPasswordNotifierImpl adminPasswordNotifier;

    @BeforeEach
    public void setup() {
        webDriverHandler.removeAllCookies();
        adminPasswordNotifier.clear();
    }

    @Test
    public void should_send_admin_password_when_fail() throws InterruptedException {
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.open();
        adminLoginPage.loginWith("123");
        assertEquals("密码已经发出", adminLoginPage.error());
        assertNotNull(adminPasswordNotifier.getPassword());
        assertTrue(new BCryptPasswordEncoder().matches(adminPasswordNotifier.getPassword(), adminPasswordNotifier.getEncodedPassword()));
    }

    @Test
    public void should_success_when_match() throws InterruptedException {
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.open();
        adminPasswordNotifier.setEncodedPassword(new BCryptPasswordEncoder().encode("123456"));
        adminLoginPage.loginWith("123456");
        Cookie authorization = webDriverHandler.getCookie("AdminAuthorization");
        assertNotNull(authorization);
    }

    @Test
    public void not_allowed_other_when_not_login() {
        TestNeedAdminPage adminConsolePage = new TestNeedAdminPage(webDriverHandler);
        adminConsolePage.open();
        webDriverHandler.removeAllCookies();
        adminConsolePage.open();
        assertTrue(adminConsolePage.content().contains("无权限"));
    }

    @Test
    public void should_visit_when_logon_and_no_page() {
        adminLogin();
        TestNeedAdminPage adminConsolePage = new TestNeedAdminPage(webDriverHandler);
        adminConsolePage.open();
        assertTrue(adminConsolePage.content().contains("hello"));
    }

}
