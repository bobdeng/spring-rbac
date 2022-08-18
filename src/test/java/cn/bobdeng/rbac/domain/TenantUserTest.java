package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.domain.rbac.RbacImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TenantUserTest {

    @Test
    public void add_user_to_tenant() {
        Tenant tenant = new Tenant(100, null);
        Tenant.Users users = mock(Tenant.Users.class);
        tenant.setUsers(users);
        UserDescription userDescription = new UserDescription("bob");
        RbacImpl rbac = new RbacImpl(users, null, null, null);

        rbac.addUser(userDescription);

        verify(users).save(new User(userDescription));
    }

}
