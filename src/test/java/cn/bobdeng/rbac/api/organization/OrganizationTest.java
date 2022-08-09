package cn.bobdeng.rbac.api.organization;

import cn.bobdeng.rbac.api.E2ETest;
import cn.bobdeng.rbac.api.UserWithTenantFixture;
import cn.bobdeng.rbac.server.dao.OrganizationDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrganizationTest extends E2ETest {
    @Autowired
    OrganizationFixture organizationFixture;
    @Autowired
    UserWithTenantFixture userWithTenantFixture;

    @BeforeEach
    public void setup() {
        userWithTenantFixture.init();
        organizationFixture.clear();
    }

    @Test
    public void should_show_organization_tree() {
        organizationFixture.newOne(userWithTenantFixture.getTenant());
        OrganizationsPage page = new OrganizationsPage(webDriverHandler);
        page.open();
        page.waitUntilNoSpin();
        page.waitUntil(() -> page.hasText("总公司"), 1000);
    }

    @Test
    public void add_sub_organization() {
        organizationFixture.newOne(userWithTenantFixture.getTenant());
        OrganizationsPage page = new OrganizationsPage(webDriverHandler);
        page.open();
        page.waitUntilNoSpin();
        page.waitUntil(() -> page.hasText("总公司"), 1000);
        page.clickContent("总公司");
        page.clickAdd();
        page.inputName("分公司");
        page.save();
        page.waitUntilNoButtonSpin();
        List<OrganizationDO> orgs = organizationFixture.list(userWithTenantFixture.getTenant());
        assertEquals(2,orgs.size());
        OrganizationDO organizationDO = orgs.get(1);
        assertEquals(userWithTenantFixture.getTenant().identity(),organizationDO.getTenantId());
        assertEquals(orgs.get(0).getId(),organizationDO.getParentId());
        assertEquals("分公司",organizationDO.getName());
    }
}
