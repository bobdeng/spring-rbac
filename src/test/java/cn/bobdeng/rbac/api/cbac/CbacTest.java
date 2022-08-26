package cn.bobdeng.rbac.api.cbac;

import cn.bobdeng.rbac.api.E2ETest;
import cn.bobdeng.rbac.api.UserWithTenantFixture;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.cbac.*;
import cn.bobdeng.rbac.server.dao.CbacContextDAO;
import cn.bobdeng.rbac.server.dao.CbacContextDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CbacTest extends E2ETest {
    @Autowired
    UserWithTenantFixture userWithTenantFixture;
    private Tenant tenant;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    CbacContextDAO cbacContextDAO;
    private CbacContext.Cbac cbac;
    private ContextDescription description;
    private ContextObject object;

    @BeforeEach
    public void setup() {
        userWithTenantFixture.init();
        tenant = userWithTenantFixture.getTenant();
        clearTable.clearTable("t_cbac_context");
        cbac = tenantRepository.cbacContext().asCbac(tenant);
    }

    @Test
    public void should_save_cbac_context() {
        getDescription();
        Context context = cbac.newContext(new Context(description));
        List<CbacContextDO> contexts = cbacContextDAO.findByTenantIdAndObjectTypeAndObjectId(tenant.identity(), "mission", "100");
        assertEquals(1, contexts.size());
        Context savedContext = contexts.get(0).toEntity();
        assertEquals(context, savedContext);
        assertEquals(context.description(), description);
    }

    private ContextDescription getDescription() {
        ContextAuthority authority = ContextAuthority.builder()
                .withUser(100)
                .build();
        object = new ContextObject("mission", "100");
        description = new ContextDescription(object, authority);
        return description;
    }

    @Test
    public void should_allow_context() {
        cbacContextDAO.save(new CbacContextDO(new Context(getDescription()), tenant));

        assertTrue(cbac.isAllowed(object, ContextAuthority.builder().withUser(100).build()));
    }
}
