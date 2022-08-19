package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.E2ETest;
import cn.bobdeng.rbac.api.UserWithTenantFixture;
import cn.bobdeng.rbac.domain.rbac.LoginName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static cn.bobdeng.rbac.api.user.PermissionTest.assertPermissionAnnotation;
import static org.junit.jupiter.api.Assertions.*;

public class UserLoginNameTest extends E2ETest {
    @Autowired
    UserWithTenantFixture userWithTenantFixture;

    @BeforeEach
    public void setup() {
        userWithTenantFixture.init();
        userLogin(userWithTenantFixture.user());
    }

    @Test
    public void should_show_login_name() {
        ListUserPage listUserPage = new ListUserPage(webDriverHandler);
        listUserPage.open();
        listUserPage.waitUntilNoSpin();
        listUserPage.clickContent("登录名");
        waitUntil(() -> listUserPage.hasText("用户登录名"), 1000);
        waitUntil(() -> listUserPage.hasText("bobdeng"), 1000);
    }

    @Test
    public void should_delete_login_name() {
        ListUserPage listUserPage = new ListUserPage(webDriverHandler);
        listUserPage.open();
        listUserPage.waitUntilNoSpin();
        listUserPage.clickContent("登录名");
        waitUntil(() -> listUserPage.hasText("解 绑"), 1000);
        listUserPage.clickUnbind();
        waitUntil(() -> listUserPage.hasText("解绑成功"), 1000);
        assertFalse(userWithTenantFixture.getRbac().loginNames().findByUser(userWithTenantFixture.user().identity()).isPresent());
    }

    @Test
    public void should_bind_login_name_to_user() {
        userWithTenantFixture.getRbac().loginNames().findByLoginName("bobdeng").ifPresent(loginName -> {
            userWithTenantFixture.getRbac().loginNames().delete(loginName.getId());
        });
        ListUserPage listUserPage = new ListUserPage(webDriverHandler);
        listUserPage.open();
        listUserPage.waitUntilNoSpin();
        listUserPage.clickContent("登录名");
        waitUntil(() -> listUserPage.hasText("绑 定"), 1000);
        listUserPage.inputLoginName("bobdeng1");
        listUserPage.clickBind();
        waitUntil(() -> listUserPage.hasText("绑定成功"), 1000);
        LoginName loginName = userWithTenantFixture.getRbac().loginNames().findByUser(userWithTenantFixture.user().identity()).get();
        assertEquals("bobdeng1", loginName.description().getName());
    }

    @Test
    public void permission_test() {
        assertPermissionAnnotation(UserLoginNameController.class, "newLoginName", new String[]{"user.login_name"});
        assertPermissionAnnotation(UserLoginNameController.class, "deleteLoginName", new String[]{"user.login_name"});
    }
}
