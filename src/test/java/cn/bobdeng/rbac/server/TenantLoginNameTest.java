package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class TenantLoginNameTest {
    @Autowired
    TenantRepository tenantRepository;
    @Autowired
    LoginNameDAO loginNameDAO;

    @Test
    public void new_loginName() {
        Tenant tenant = new Tenant(new TenantDescription("租户1"));
        tenant = tenantRepository.save(tenant);
        User bob = tenant.addUser(new UserDescription("bob"));
        tenant.addLoginName(new LoginNameDescription("bob", bob));
        List<LoginNameDO> loginNames = StreamSupport.stream(loginNameDAO.findAll().spliterator(), false).toList();
        assertEquals(1, loginNames.size());
    }

    @Test
    public void find_user_by_login_name() {
        Tenant tenant = new Tenant(new TenantDescription("租户1"));
        tenant = tenantRepository.save(tenant);
        User bob = tenant.addUser(new UserDescription("bob"));
        tenant.addLoginName(new LoginNameDescription("bob", bob));

        LoginName loginName = tenant.getLoginNames().findByLoginName("bob").orElseThrow();
        assertEquals("bob", loginName.description().getName());
    }
}
