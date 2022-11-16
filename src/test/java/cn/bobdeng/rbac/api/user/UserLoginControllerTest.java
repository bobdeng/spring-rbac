package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.E2ETest;
import cn.bobdeng.rbac.api.UserWithTenantFixture;
import cn.bobdeng.rbac.api.pages.AdminLoginPage;
import cn.bobdeng.rbac.security.SessionStore;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static cn.bobdeng.rbac.api.user.PermissionTest.assertPermissionAnnotation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserLoginControllerTest extends E2ETest {
    @Autowired
    UserWithTenantFixture userWithTenantFixture;
    @Autowired
    SessionStore sessionStore;

    @BeforeEach
    public void setup() {
        userWithTenantFixture.init();
        sessionStore.setTenant(userWithTenantFixture.getTenant());
        clearLogin();
    }

    @Test
    public void should_login_success() {
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.loginWith("bobdeng", "123456");
        adminLoginPage.waitUntilNoButtonSpin();
        adminLoginPage.userLoginSuccess();
    }

    @Test
    public void should_login_fail_when_login_name_not_exist() {
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.loginWith("bobdeng1", "123456");
        adminLoginPage.waitUntilNoButtonSpin();
        adminLoginPage.userLoginFail();
    }

    @Test
    public void should_login_fail_when_password_error() {
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.loginWith("bobdeng", "123455");
        adminLoginPage.waitUntilNoButtonSpin();
        adminLoginPage.userLoginFail();
    }

    @Test
    public void should_login_fail_when_user_locked() {
        userWithTenantFixture.user().lock();
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.loginWith("bobdeng", "123456");
        adminLoginPage.waitUntilNoButtonSpin();
        adminLoginPage.userLocked();
    }

    @Test
    public void should_has_permission_annotation() {
        assertPermissionAnnotation(UserController.class, "newUser", new String[]{"user.create"});
    }

    @Test
    public void should_remove_cookies_when_logout() throws IOException {
        Response response = okHttpClient.newCall(new Request.Builder()
                .delete()
                .url(webDriverHandler.getBaseUrl() + "/api/1.0/user_sessions")
                .build()).execute();
        assertTrue(response.isSuccessful());
        List<String> cookies = response.headers().values("Set-Cookie");
        assertEquals("Authorization=; Max-Age=0; Expires=Thu, 01-Jan-1970 00:00:10 GMT; Path=/", cookies.get(0));
    }
}