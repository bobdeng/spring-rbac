package cn.bobdeng.rbac.api.cbac;

import cn.bobdeng.rbac.JsonPage;
import cn.bobdeng.rbac.api.E2ETest;
import cn.bobdeng.rbac.api.UserWithTenantFixture;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.cbac.*;
import cn.bobdeng.rbac.domain.organization.*;
import cn.bobdeng.rbac.server.dao.CbacContextDAO;
import cn.bobdeng.rbac.server.dao.CbacContextDO;
import cn.bobdeng.rbac.server.dao.EmployeeDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CbacTest extends E2ETest {
    @Autowired
    UserWithTenantFixture userWithTenantFixture;
    private Tenant tenant;
    @Autowired
    CbacContextDAO cbacContextDAO;
    @Autowired
    CbacContext cbacContext;
    @Autowired
    OrganizationContext organizationContext;
    @Autowired
    EmployeeDAO employeeDAO;
    private CbacContext.Cbac cbac;
    private ContextDescription description;
    private ContextObject object;

    @BeforeEach
    public void setup() {
        userWithTenantFixture.init();
        tenant = userWithTenantFixture.getTenant();
        clearTable.clearTable("t_cbac_context");
        clearTable.clearTable("t_rbac_organization");
        clearTable.clearTable("t_rbac_employee");
        cbac = cbacContext.asCbac(tenant);
        userLogin(userWithTenantFixture.user());
    }

    @Test
    public void should_save_cbac_context() {
        getDescription(100);
        Context context = cbac.newContext(new Context(description));
        List<CbacContextDO> contexts = cbacContextDAO.findByTenantIdAndObjectTypeAndObjectId(tenant.identity(), "mission", "100");
        assertEquals(1, contexts.size());
        Context savedContext = contexts.get(0).toEntity();
        assertEquals(context, savedContext);
        assertEquals(context.description(), description);
    }

    private ContextDescription getDescription(int userId) {
        ContextAuthority authority = ContextAuthority.builder()
                .withUser(userId)
                .withRoles("角色1")
                .withOrganizations("部门1")
                .build();
        object = new ContextObject("mission", "100");
        description = new ContextDescription(object, authority);
        return description;
    }

    @Test
    public void should_allow_context() {
        cbacContextDAO.save(new CbacContextDO(new Context(getDescription(100)), tenant));

        assertTrue(cbac.isAllowed(object, ContextAuthority.builder().withUser(100).build()));
    }

    @Test
    public void should_not_allow_context() {
        cbacContextDAO.save(new CbacContextDO(new Context(getDescription(100)), tenant));

        assertFalse(cbac.isAllowed(object, ContextAuthority.builder().withUser(101).build()));
    }

    @Test
    public void should_not_allow_with_annotation() {
        cbacContextDAO.save(new CbacContextDO(new Context(getDescription(100)), tenant));
        JsonPage jsonPage = new JsonPage(webDriverHandler);
        jsonPage.open("/cbac/101");
        String content = jsonPage.content();
        assertEquals("无权限", content);
    }

    @Test
    public void should_allow_with_annotation() {
        cbacContextDAO.save(new CbacContextDO(new Context(getDescription(userWithTenantFixture.user().getId())), tenant));
        JsonPage jsonPage = new JsonPage(webDriverHandler);
        jsonPage.open("/cbac/100");
        String content = jsonPage.content();
        assertEquals("通过", content);
    }

    @Test
    public void should_allow_with_role() {
        ContextAuthority authority = ContextAuthority.builder()
                .withRoles("角色1")
                .build();
        object = new ContextObject("mission", "100");
        description = new ContextDescription(object, authority);
        cbacContextDAO.save(new CbacContextDO(new Context(description), tenant));
        JsonPage jsonPage = new JsonPage(webDriverHandler);
        jsonPage.open("/cbac/100");
        String content = jsonPage.content();
        assertEquals("通过", content);
    }

    @Test
    public void should_allow_with_organization() {
        Organization organization = organizationContext.asOrganization(userWithTenantFixture.getTenant())
                .add(new OrganizationDescription("部门1", 1));
        organization.addEmployee(userWithTenantFixture.user());
        ContextAuthority authority = ContextAuthority.builder()
                .withOrganizations("部门1")
                .build();
        object = new ContextObject("mission", "100");
        description = new ContextDescription(object, authority);
        cbacContextDAO.save(new CbacContextDO(new Context(description), tenant));
        JsonPage jsonPage = new JsonPage(webDriverHandler);
        jsonPage.open("/cbac/100");
        String content = jsonPage.content();
        assertEquals("通过", content);
    }
}
