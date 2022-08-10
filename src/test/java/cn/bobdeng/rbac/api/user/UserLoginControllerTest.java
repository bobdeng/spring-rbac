package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.E2ETest;
import cn.bobdeng.rbac.api.UserWithTenantFixture;
import cn.bobdeng.rbac.api.pages.AdminLoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static cn.bobdeng.rbac.api.user.PermissionTest.assertPermissionAnnotation;

class UserLoginControllerTest extends E2ETest {
    @Autowired
    UserWithTenantFixture userWithTenantFixture;

    @BeforeEach
    public void setup() {
        userWithTenantFixture.init();
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
}