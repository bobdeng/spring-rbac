package cn.bobdeng.rbac.api;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class LoginPageTest {
    @Autowired
    WebDriverHandler webDriverHandler;
    private LoginPage loginPage;

    @Test
    public void test() {
        assertNotNull(webDriverHandler.WEBDRIVER);
        loginPage = new LoginPage(webDriverHandler);
        loginPage.open();
    }

    @AfterAll
    public static void tearDown() {
        WebDriverHandler.WEBDRIVER.close();
    }
}
