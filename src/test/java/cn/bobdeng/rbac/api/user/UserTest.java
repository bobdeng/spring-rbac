package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.E2ETest;
import cn.bobdeng.rbac.api.UserWithTenantFixture;
import cn.bobdeng.rbac.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest extends E2ETest {
    @Autowired
    UserWithTenantFixture userWithTenantFixture;

    @BeforeEach
    public void setup() {
        userWithTenantFixture.init();
        userLogin(userWithTenantFixture.user());
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
        User lisi = userWithTenantFixture.getTenant().users().findByName("李四").get(0);
        assertTrue(lisi.verifyPassword("1344444"));
        List<Role> roles = lisi.userRoles().list().collect(Collectors.toList());
        assertEquals(1, roles.size());
        LoginName loginNameOfLisi = userWithTenantFixture.getTenant().loginNames().findByLoginName("lisi").get();
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
        listUserPage.inputById("李\n", "search");
        listUserPage.waitUntilNoSpin();
        assertFalse(listUserPage.hasText("张三"));//in fixture
    }
}
