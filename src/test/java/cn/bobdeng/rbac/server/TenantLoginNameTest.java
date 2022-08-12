package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.archtype.LoginNameDescription;
import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.server.dao.LoginNameDAO;
import cn.bobdeng.rbac.server.dao.LoginNameDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TenantLoginNameTest extends BaseTest{
    @Autowired
    TenantRepository tenantRepository;
    @Autowired
    LoginNameDAO loginNameDAO;

    @BeforeEach
    public void setup() {
        clearTable.clearTable("t_rbac_tenant");
        clearTable.clearTable("t_rbac_login_name");
    }

    @Test
    public void new_loginName() {
        Tenant tenant = new Tenant(new TenantDescription("租户1"));
        tenant = tenantRepository.save(tenant);
        User bob = tenant.addUser(new UserDescription("bob"));
        tenant.addLoginName(new LoginNameDescription("bob", bob.identity()));
        List<LoginNameDO> loginNames = StreamSupport.stream(loginNameDAO.findAll().spliterator(), false).collect(Collectors.toList());
        assertEquals(1, loginNames.size());
    }

    @Test
    public void find_user_by_login_name() {
        Tenant tenant = new Tenant(new TenantDescription("租户1"));
        tenant = tenantRepository.save(tenant);
        User bob = tenant.addUser(new UserDescription("bob"));
        tenant.addLoginName(new LoginNameDescription("bob", bob.identity()));

        LoginName loginName = tenant.loginNames().findByLoginName("bob").orElseThrow(RuntimeException::new);
        assertEquals("bob", loginName.description().getName());
    }
}
