package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.server.dao.RoleDAO;
import cn.bobdeng.rbac.server.dao.RoleDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class RoleTest extends E2ETest {
    @Autowired
    RoleDAO roleDAO;
    @Autowired
    TenantRepository tenantRepository;
    private Tenant tenant;

    @BeforeEach
    public void setup() {
        tenant = tenantRepository.save(new Tenant(new TenantDescription("租户1")));
    }

    @Test
    public void should_show_all_test() {
        roleDAO.save(new RoleDO(new Role(null, new RoleDescription("角色1", Arrays.asList("user.create"))), tenant));
    }
}
