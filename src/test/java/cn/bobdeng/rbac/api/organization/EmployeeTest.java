package cn.bobdeng.rbac.api.organization;

import cn.bobdeng.rbac.api.E2ETest;
import cn.bobdeng.rbac.api.UserWithTenantFixture;
import cn.bobdeng.rbac.server.dao.OrganizationDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class EmployeeTest extends E2ETest {
    @Autowired
    OrganizationFixture organizationFixture;
    @Autowired
    UserWithTenantFixture userWithTenantFixture;
    @Autowired
    EmployeeFixture employeeFixture;

    @BeforeEach
    public void setup() {
        userWithTenantFixture.init();
        organizationFixture.clear();
        employeeFixture.clear();
        OrganizationDO organizationDO = organizationFixture.newOne(userWithTenantFixture.getTenant());
        employeeFixture.set(organizationDO.getId(), userWithTenantFixture.user());
        userLogin(userWithTenantFixture.user());
    }

    @Test
    public void should_list_employees_of_organization() {
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
}
