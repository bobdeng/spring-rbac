package cn.bobdeng.rbac.api.role;

import cn.bobdeng.rbac.JsonPage;
import cn.bobdeng.rbac.api.E2ETest;
import cn.bobdeng.rbac.api.pages.ListTenantRolePage;
import cn.bobdeng.rbac.api.pages.NewTenantRolePage;
import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.domain.rbac.Role;
import cn.bobdeng.rbac.domain.rbac.RoleDescription;
import cn.bobdeng.rbac.domain.rbac.User;
import cn.bobdeng.rbac.domain.rbac.UserDescription;
import cn.bobdeng.rbac.security.SessionStore;
import cn.bobdeng.rbac.server.dao.RoleDAO;
import cn.bobdeng.rbac.server.dao.RoleDO;
import cn.bobdeng.rbac.server.dao.UserRoleDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class RoleTest extends E2ETest {
    @Autowired
    RoleDAO roleDAO;
    @Autowired
    UserRoleDAO userRoleDAO;
    @Autowired
    TenantRepository tenantRepository;
    @Autowired
    SessionStore sessionStore;
    private Tenant tenant;

    @BeforeEach
    public void setup() {
        adminLogin();
        clearTable.clearTable("t_rbac_tenant");
        clearTable.clearTable("t_rbac_role");
        tenant = tenantRepository.save(new Tenant(new TenantDescription("租户1")));
        sessionStore.setTenant(tenant);
    }

    @Test
    public void should_show_all_roles() {
        roleDAO.save(new RoleDO(new Role(null, new RoleDescription("角色1", Arrays.asList("user.create"))), tenant));
        ListTenantRolePage listTenantRolePage = new ListTenantRolePage(webDriverHandler);
        listTenantRolePage.open(tenant);
        listTenantRolePage.waitUntilNoSpin();
        assertTrue(listTenantRolePage.hasText("角色1"));
    }

    @Test
    public void should_save_new_role() {
        ListTenantRolePage listTenantRolePage = new ListTenantRolePage(webDriverHandler);
        listTenantRolePage.open(tenant);
        listTenantRolePage.waitUntilNoSpin();
        listTenantRolePage.clickButton("新 增");
        NewTenantRolePage newTenantRolePage = new NewTenantRolePage(webDriverHandler);
        newTenantRolePage.inputById("角色1", "inputName");
        newTenantRolePage.clickContent("角色管理");
        newTenantRolePage.clickButton("确 定");
        newTenantRolePage.waitUntilNoButtonSpin();
        List<RoleDO> roles = roleDAO.findAllByTenantId(tenant.identity());
        assertEquals(1, roles.size());
        assertThat(roles.get(0).toEntity().description().getAllows()).hasSameElementsAs(
                Arrays.asList("role", "role.create", "role.del", "role.edit")
        );
    }

    @Test
    public void should_edit_role() {
        roleDAO.save(new RoleDO(new Role(null, new RoleDescription("角色1", Arrays.asList("role.create"))), tenant));
        ListTenantRolePage listTenantRolePage = new ListTenantRolePage(webDriverHandler);
        listTenantRolePage.open(tenant);
        listTenantRolePage.waitUntilNoSpin();
        listTenantRolePage.clickButton("修改");
        EditRolePage editRolePage = new EditRolePage(webDriverHandler);
        editRolePage.waitUntil(() -> editRolePage.name().equals("角色1"), 1000);
        assertEquals("角色1", editRolePage.name());
        editRolePage.inputById("角色2", "inputName");
        editRolePage.clickContent("角色管理");
        editRolePage.clickButton("确 定");
        editRolePage.waitUntilNoButtonSpin();
        assertTrue(editRolePage.hasText("保存成功"));
        List<RoleDO> roles = roleDAO.findAllByTenantId(tenant.identity());
        assertEquals(1, roles.size());
        assertThat(roles.get(0).toEntity().description().getAllows()).hasSameElementsAs(
                Arrays.asList("role", "role.create", "role.del", "role.edit")
        );
    }

    @Test
    public void should_show_error_when_role_not_found() throws Exception {
        JsonPage jsonPage = new JsonPage(webDriverHandler);
        jsonPage.open("/api/1.0/tenants/" + (tenant.identity() - 100) + "/roles/12");
        assertEquals("没有发现记录", jsonPage.content());
    }

    @Test
    public void should_delete_role() {
        Role role1 = roleDAO.save(new RoleDO(new Role(null, new RoleDescription("角色1", Arrays.asList("role.create"))), tenant))
                .toEntity();
        User bob = tenantRepository.rbacContext().asRbac(tenant).addUser(new UserDescription("bob"));
        bob.setRoles(Arrays.asList(role1));

        ListTenantRolePage listTenantRolePage = new ListTenantRolePage(webDriverHandler);
        listTenantRolePage.open(tenant);
        listTenantRolePage.waitUntilNoSpin();
        listTenantRolePage.clickButton("删除");
        listTenantRolePage.waitUntil(() -> listTenantRolePage.hasText("OK"), 1000);
        listTenantRolePage.clickContent("OK");
        listTenantRolePage.waitUntilNoSpin();
        waitUntil(() -> listTenantRolePage.hasText("删除成功"), 1000);
        List<RoleDO> roles = roleDAO.findAllByTenantId(tenant.identity());
        assertEquals(0, roles.size());
        assertEquals(0, userRoleDAO.findAllByUserId(bob.getId()).size());
    }
}
