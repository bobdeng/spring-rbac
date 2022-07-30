package cn.bobdeng.rbac.api;

import org.junit.jupiter.api.AfterAll;

public class E2ETest {
    @AfterAll
    public static void tearDown() {
        WebDriverHandler.WEBDRIVER.close();
    }
}
