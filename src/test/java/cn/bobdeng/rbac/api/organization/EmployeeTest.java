package cn.bobdeng.rbac.api.organization;

import cn.bobdeng.rbac.api.E2ETest;
import cn.bobdeng.rbac.api.UserWithTenantFixture;
import cn.bobdeng.rbac.server.dao.OrganizationDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeTest extends E2ETest {
    @Autowired
    OrganizationFixture organizationFixture;
    @Autowired
    UserWithTenantFixture userWithTenantFixture;
    @Autowired
    EmployeeFixture employeeFixture;
    private OrganizationDO organizationDO;

    @BeforeEach
    public void setup() {
        userWithTenantFixture.init();
        organizationFixture.clear();
        employeeFixture.clear();
        organizationDO = organizationFixture.newOne(userWithTenantFixture.getTenant());
        userLogin(userWithTenantFixture.user());
    }

    @Test
    public void should_list_employees_of_organization() {
        employeeFixture.set(organizationDO.getId(), userWithTenantFixture.user());
        OrganizationsPage organizationsPage = new OrganizationsPage(webDriverHandler);
        organizationsPage.open();
        organizationsPage.waitUntilNoSpin();
        organizationsPage.waitUntil(() -> organizationsPage.hasText("总公司"), 1000);
        organizationsPage.clickContent("总公司");
        organizationsPage.waitUntilNoSpin();
        organizationsPage.waitUntil(() -> organizationsPage.hasText("张三"), 1000);
    }

    @Test
    public void should_remove_employee_from_organization() {
        employeeFixture.set(organizationDO.getId(), userWithTenantFixture.user());
        OrganizationsPage organizationsPage = new OrganizationsPage(webDriverHandler);
        organizationsPage.open();
        organizationsPage.waitUntilNoSpin();
        organizationsPage.waitUntil(() -> organizationsPage.hasText("总公司"), 1000);
        organizationsPage.clickContent("总公司");
        organizationsPage.waitUntilNoSpin();
        organizationsPage.waitUntil(() -> organizationsPage.hasText("张三"), 1000);
        organizationsPage.clickContent("删除");
        organizationsPage.waitUntilNoSpin();
        organizationsPage.waitUntil(() -> organizationsPage.hasText("删除成功"), 1000);
        organizationsPage.waitUntilNoSpin();
        waitUntil(() -> !organizationsPage.hasText("张三"), 1000);
    }

    @Test
    public void should_add_employee() {
        OrganizationsPage organizationsPage = new OrganizationsPage(webDriverHandler);
        organizationsPage.open();
        organizationsPage.waitUntilNoSpin();
        organizationsPage.waitUntil(() -> organizationsPage.hasText("总公司"), 1000);
        organizationsPage.clickContent("总公司");
        organizationsPage.waitUntilNoSpin();

        organizationsPage.clickContent("添 加");
        SelectUserPage selectUserPage = new SelectUserPage(webDriverHandler);
        selectUserPage.search("张");
        selectUserPage.waitUntilNoSpin();
        selectUserPage.select("张三");
        organizationsPage.waitUntilNoSpin();
        waitUntil(() -> !organizationsPage.hasText("选择用户"), 1000);
        assertTrue(organizationsPage.hasText("操作成功"));
        waitUntil(() -> organizationsPage.hasText("张三"), 1000);
    }
}
