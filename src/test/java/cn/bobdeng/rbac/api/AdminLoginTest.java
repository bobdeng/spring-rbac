package cn.bobdeng.rbac.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.junit.runner.RunWith;
import org.openqa.selenium.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

public class AdminLoginTest extends E2ETest {
    @Autowired
    WebDriverHandler webDriverHandler;
    @Autowired
    AdminPasswordNotifierImpl adminPasswordNotifier;

    @BeforeEach
    public void setup() {
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

}
