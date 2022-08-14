package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.api.pages.AdminLoginPage;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = "/wxconfig.properties")
public class WxLoginTest extends E2ETest {
    @Test
    public void 当配置了微信显示微信登录按钮() {
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.open();
        adminLoginPage.waitUntilNoButtonSpin();
        adminLoginPage.waitUntil(() -> adminLoginPage.hasText("微信登录"), 1000);
    }
}
