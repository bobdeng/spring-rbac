package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class UserPasswordTest {
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    PasswordDAO passwordDAO;

    @Test
    public void save_password() {
        Tenant tenant = new Tenant(new TenantDescription("租户1"));
        tenant = tenantRepository.save(tenant);
        User bob = tenant.addUser(new UserDescription("bob"));

        bob.savePassword(new RawPassword("123456"));

        Password password = passwordDAO.findById(bob.getId()).orElseThrow(RuntimeException::new);
        assertTrue(new BCryptPasswordEncoder().matches("123456", password.description().getPassword()));
        assertEquals(password.identity(), bob.identity());
    }
}
