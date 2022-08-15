package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.api.oauth.WxConfig;
import cn.bobdeng.rbac.api.pages.AdminLoginPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class WxLoginTest extends E2ETest {
    @Autowired
    WxConfig wxConfig;

    @Test
    public void 当配置了微信显示微信登录按钮() {
        wxConfig.setEnabled(true);
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.open();
        adminLoginPage.waitUntilNoButtonSpin();
        adminLoginPage.waitUntil(() -> adminLoginPage.hasText("微信登录"), 1000);
        adminLoginPage.loginByWeixin();
        String currentUrl = WebDriverHandler.WEBDRIVER.getCurrentUrl();
        assertEquals("https://open.weixin.qq.com/connect/qrconnect?appid=123456&redirect_uri=https%3A%2F%2Ftest.com%2Fwx_callback&response_type=code&scope=snsapi_login&state=123456#wechat_redirect", currentUrl);
    }

    @Test
    public void 当没有配置微信不显示微信登录按钮() {
        wxConfig.setEnabled(false);
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.open();
        adminLoginPage.waitUntilNoButtonSpin();
        assertFalse(adminLoginPage.hasText("微信登录"));
    }
}
