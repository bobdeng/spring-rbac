package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.api.pages.ListTenantRolePage;
import cn.bobdeng.rbac.api.pages.NewTenantRolePage;
import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.server.dao.RoleDAO;
import cn.bobdeng.rbac.server.dao.RoleDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoleTest extends E2ETest {
    @Autowired
    RoleDAO roleDAO;
    @Autowired
    TenantRepository tenantRepository;
    private Tenant tenant;

    @BeforeEach
    public void setup() {
        clearTable("t_rbac_tenant");
        clearTable("t_rbac_role");
        tenant = tenantRepository.save(new Tenant(new TenantDescription("租户1")));
    }

    @Test
    public void should_show_all_roles() {
        roleDAO.save(new RoleDO(new Role(null, new RoleDescription("角色1", Arrays.asList("user.create"))), tenant));
        ListTenantRolePage listTenantRolePage = new ListTenantRolePage(webDriverHandler);
        listTenantRolePage.open(tenant);
        listTenantRolePage.waitUntilNoSpin();
        assertFalse(listTenantRolePage.hasNoData());
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
    }
}
