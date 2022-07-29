package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TenantUserTest {
    private TenantRepository tenantRepository;
    private Tenants tenants;

    @BeforeEach
    public void setup() {
        tenantRepository = mock(TenantRepository.class);
        tenants = new Tenants(tenantRepository);
    }

    @Test
    public void add_user_to_tenant() {
        Tenant tenant = new Tenant(100, null);
        Tenant.Users users = mock(Tenant.Users.class);
        tenant.setUsers(users);
        UserDescription userDescription = new UserDescription("bob");

        tenant.addUser(userDescription);

        verify(users).save(new User(userDescription));
    }

}
