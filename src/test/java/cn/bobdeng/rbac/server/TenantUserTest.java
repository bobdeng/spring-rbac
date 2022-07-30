package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantDescription;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.UserDescription;
import cn.bobdeng.rbac.server.dao.UserDAO;
import cn.bobdeng.rbac.server.dao.UserDO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class TenantUserTest {
    @Autowired
    TenantRepository tenantRepository;
    @Autowired
    UserDAO userDAO;

    @Test
    public void new_user() {
        Tenant tenant = new Tenant(new TenantDescription("租户1"));
        tenant = tenantRepository.save(tenant);
        tenant.addUser(new UserDescription("bob"));

        List<UserDO> users = StreamSupport.stream(userDAO.findAll().spliterator(), false).collect(Collectors.toList());
        assertEquals(1, users.size());
        UserDO userDO = users.get(0);
        assertEquals(tenant.identity(), userDO.getTenantId());
        assertEquals("bob", userDO.getName());
    }
}
