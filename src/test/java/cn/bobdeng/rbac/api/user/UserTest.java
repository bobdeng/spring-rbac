package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.E2ETest;
import cn.bobdeng.rbac.api.UserWithTenantFixture;
import cn.bobdeng.rbac.api.pages.AdminLoginPage;
import cn.bobdeng.rbac.domain.rbac.LoginName;
import cn.bobdeng.rbac.domain.rbac.Password;
import cn.bobdeng.rbac.domain.rbac.Role;
import cn.bobdeng.rbac.domain.rbac.User;
import cn.bobdeng.rbac.server.dao.PasswordDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest extends E2ETest {
    @Autowired
    UserWithTenantFixture userWithTenantFixture;
    @Autowired
    PasswordDAO passwordDAO;

    @BeforeEach
    public void setup() {
        userWithTenantFixture.init();
        userLogin(userWithTenantFixture.user());
        new AdminLoginPage(webDriverHandler).open();
    }

    @Test
    public void should_save_user() {
        ListUserPage listUserPage = new ListUserPage(webDriverHandler);
        listUserPage.open();
        listUserPage.waitUntilNoSpin();
        listUserPage.clickButton("新 增");
        NewUserPage newUserPage = new NewUserPage(webDriverHandler);
        newUserPage.inputById("李四", "inputName");
        newUserPage.inputById("lisi", "inputLoginName");
        newUserPage.inputById("1344444", "inputPassword");
        newUserPage.clickContent("角色1");

        newUserPage.clickButton("保 存");
        newUserPage.waitUntilNoButtonSpin();

        assertTrue(newUserPage.hasText("新增成功"));
        User lisi = userWithTenantFixture.getRbac().users().findByName("李四").get(0);
        assertTrue(lisi.verifyPassword("1344444"));
        assertEquals(User.UserStatus.Normal, lisi.description().getStatus());
        List<Role> roles = lisi.userRoles().list().collect(Collectors.toList());
        assertEquals(1, roles.size());
        LoginName loginNameOfLisi = userWithTenantFixture.getRbac().loginNames().findByLoginName("lisi").get();
        assertEquals("lisi", loginNameOfLisi.description().getName());
    }

    @Test
    public void should_list_user() {
        ListUserPage listUserPage = new ListUserPage(webDriverHandler);
        listUserPage.open();
        listUserPage.waitUntilNoSpin();
        assertTrue(listUserPage.hasText("张三"));//in fixture
    }

    @Test
    public void should_list_user_by_name() {
        ListUserPage listUserPage = new ListUserPage(webDriverHandler);
        listUserPage.open();
        listUserPage.waitUntilNoSpin();
        listUserPage.inputById("张\n", "search");
        listUserPage.waitUntilNoSpin();
        assertTrue(listUserPage.hasText("张三"));//in fixture
        listUserPage.inputById("\b李\n", "search");
        listUserPage.waitUntilNoSpin();
        listUserPage.waitUntilNoButtonSpin();
        assertFalse(listUserPage.hasText("张三"));//in fixture
    }

    @Test
    public void should_reset_user_password() {
        Password currentPassword = passwordDAO.findAll().iterator().next();
        ListUserPage listUserPage = new ListUserPage(webDriverHandler);
        listUserPage.open();
        listUserPage.waitUntilNoSpin();
        listUserPage.clickButton("重置密码");
        listUserPage.waitUntilNoButtonSpin();
        listUserPage.clickButton("确 定");
        listUserPage.waitUntilNoSpin();
        listUserPage.waitUntilNoButtonSpin();
        waitUntil(() -> listUserPage.hasText("新密码为"), 1000);
        assertNotEquals(passwordDAO.findAll().iterator().next().description().getPassword(),
                currentPassword.description().getPassword());
    }

    @Test
    public void should_change_password() {
        Password currentPassword = passwordDAO.findAll().iterator().next();

        SetPasswordPage setPasswordPage = new SetPasswordPage(webDriverHandler);
        setPasswordPage.open();
        setPasswordPage.inputCurrentPassword("123456");
        setPasswordPage.inputNewPassword("cwkidSd2");
        setPasswordPage.inputConfirmation("cwkidSd2");
        setPasswordPage.save();
        setPasswordPage.waitUntilNoButtonSpin();
        assertTrue(setPasswordPage.hasText("修改成功"));
        assertNotEquals(passwordDAO.findAll().iterator().next().description().getPassword(),
                currentPassword.description().getPassword());
    }

    @Test
    public void should_lock_user() {
        ListUserPage listUserPage = new ListUserPage(webDriverHandler);
        listUserPage.open();
        listUserPage.waitUntilNoSpin();
        listUserPage.clickButton("锁定");
        listUserPage.waitUntilNoSpin();
        assertTrue(listUserPage.hasText("用户已锁定"));
        User user = userWithTenantFixture.getRbac().users().findByIdentity(userWithTenantFixture.user().identity()).get();
        assertEquals(User.UserStatus.Locked, user.description().getStatus());
    }

    @Test
    public void should_unlock_user() {
        userWithTenantFixture.user().lock();
        ListUserPage listUserPage = new ListUserPage(webDriverHandler);
        listUserPage.open();
        listUserPage.waitUntilNoSpin();
        listUserPage.clickButton("解锁");
        listUserPage.waitUntilNoSpin();
        assertTrue(listUserPage.hasText("用户已解锁"));
        User user = userWithTenantFixture.getRbac().users().findByIdentity(userWithTenantFixture.user().identity()).get();
        assertEquals(User.UserStatus.Normal, user.description().getStatus());
    }
}
