package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.api.pages.AdminConsolePage;
import cn.bobdeng.rbac.api.pages.AdminLoginPage;
import cn.bobdeng.rbac.domain.AdminPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class AdminLoginTest extends E2ETest {
    AdminPasswordNotifierImpl adminPasswordNotifier = new AdminPasswordNotifierImpl();
    @BeforeEach
    public void setup() {
        webDriverHandler.removeAllCookies();
        adminPasswordNotifier.clear();
    }

    @Test
    public void should_send_admin_password_when_fail() {
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.open();
        adminLoginPage.loginWith("123");
        assertEquals("密码已发出", adminLoginPage.error());
        assertNotNull(adminPasswordNotifier.getPassword());
        assertTrue(new BCryptPasswordEncoder().matches(adminPasswordNotifier.getPassword(), adminPasswordNotifier.getEncodedPassword()));
    }

    @Test
    public void should_success_when_match() {
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.open();
        adminPasswordNotifier.setEncodedPassword(new BCryptPasswordEncoder().encode("123456"));
        adminLoginPage.loginWith("123456");
        assertEquals("登录成功", adminLoginPage.error());
        Cookie authorization = webDriverHandler.getCookie("AdminAuthorization");
        assertNotNull(authorization);
    }

    @Test
    public void not_allowed_other_when_not_login() {
        AdminConsolePage adminConsolePage = new AdminConsolePage(webDriverHandler);
        adminConsolePage.open();
        webDriverHandler.removeAllCookies();
        adminConsolePage.open();
        System.out.println(adminConsolePage.content());
        assertTrue(adminConsolePage.content().contains("HTTP ERROR 403"));
    }

    @Test
    public void should_visit_when_logon_and_no_page() {
        adminLogin();

        AdminConsolePage adminConsolePage = new AdminConsolePage(webDriverHandler);
        adminConsolePage.open();
        assertEquals("管理台", adminConsolePage.title());
    }

}
