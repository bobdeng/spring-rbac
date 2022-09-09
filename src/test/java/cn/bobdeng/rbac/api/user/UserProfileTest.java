package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.Cookies;
import cn.bobdeng.rbac.JsonPage;
import cn.bobdeng.rbac.api.E2ETest;
import cn.bobdeng.rbac.api.UserWithTenantFixture;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class UserProfileTest extends E2ETest {
    @Autowired
    UserWithTenantFixture userWithTenantFixture;

    @Test
    public void 当没有登录读取Session返回404() {
        clearLogin();
        JsonPage jsonPage = new JsonPage(webDriverHandler);
        jsonPage.open("/session");
        assertEquals("没有发现记录", jsonPage.content());
    }

    @Test
    public void 登录读取Session() {
        userWithTenantFixture.init();
        userLogin(userWithTenantFixture.user());
        JsonPage jsonPage = new JsonPage(webDriverHandler);
        jsonPage.open("/session");
        assertTrue(jsonPage.content().contains("张三"));
    }

    @Test
    public void 当Admin登录读取Session() {
        userWithTenantFixture.init();
        adminLogin();
        JsonPage jsonPage = new JsonPage(webDriverHandler);
        jsonPage.open("/session");
        assertTrue(jsonPage.content().contains("系统管理员"));
    }
    @Test
    public void 当Token错误(){
        loginWithErrorToken();
        JsonPage jsonPage = new JsonPage(webDriverHandler);
        jsonPage.open("/session");
        assertEquals("没有发现记录", jsonPage.content());
        Cookie cookie = webDriverHandler.getCookie(Cookies.AUTHORIZATION);
        assertNull(cookie);
    }
}
