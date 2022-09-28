package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.E2ETest;
import cn.bobdeng.rbac.api.UserWithTenantFixture;
import cn.bobdeng.rbac.server.dao.UserRoleDAO;
import cn.bobdeng.rbac.server.dao.UserRoleDO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRoleControllerTest extends E2ETest {
    @Autowired
    UserWithTenantFixture userWithTenantFixture;
    @Autowired
    UserRoleDAO userRoleDAO;

    @Test
    public void should_show_saved_roles() {
        userWithTenantFixture.init();
        userLogin(userWithTenantFixture.user());
        ListUserPage listUserPage = new ListUserPage(webDriverHandler);
        listUserPage.open();
        listUserPage.clickSetRole();
        SetRolePage setRolePage = new SetRolePage(webDriverHandler);
        setRolePage.isRoleChecked();
    }

    @Test
    public void should_show_save_roles() {
        userWithTenantFixture.init();
        userLogin(userWithTenantFixture.user());
        ListUserPage listUserPage = new ListUserPage(webDriverHandler);
        listUserPage.open();
        listUserPage.clickSetRole();
        SetRolePage setRolePage = new SetRolePage(webDriverHandler);
        setRolePage.isRoleChecked();
        setRolePage.clickRole();
        setRolePage.save();
        setRolePage.waitUntil(() -> setRolePage.hasText("保存成功"), 1000);

        List<UserRoleDO> roles = userRoleDAO.findAllByUserId(userWithTenantFixture.user().identity());
        assertEquals(0, roles.size());
    }
}