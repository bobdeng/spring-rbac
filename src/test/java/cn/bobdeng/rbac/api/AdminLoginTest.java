package cn.bobdeng.rbac.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminLoginTest extends E2ETest {
    @Autowired
    WebDriverHandler webDriverHandler;
    @Autowired
    AdminPasswordNotifierImpl adminPasswordNotifier;
    @BeforeEach
    public void setup(){
        adminPasswordNotifier.clear();
    }
    @Test
    public void should_send_admin_password_when_fail() {
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.open();
        adminLoginPage.loginWith("123");
        assertEquals("密码已发出",adminLoginPage.error());
        assertNotNull(adminPasswordNotifier.getPassword());
    }
}
