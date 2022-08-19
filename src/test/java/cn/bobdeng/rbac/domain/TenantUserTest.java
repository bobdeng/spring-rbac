package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.domain.rbac.RbacImpl;
import cn.bobdeng.rbac.domain.rbac.User;
import cn.bobdeng.rbac.domain.rbac.UserDescription;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class TenantUserTest {

    @Test
    public void add_user_to_tenant() {
        RbacContext.Users users = mock(RbacContext.Users.class);
        UserDescription userDescription = new UserDescription("bob");
        RbacContext rbacContext = mock(RbacContext.class);
        Tenant tenant = new Tenant();
        RbacImpl rbac = new RbacImpl(tenant, rbacContext);
        when(rbacContext.users(tenant)).thenReturn(users);

        rbac.addUser(userDescription);

        verify(users).save(new User(userDescription));
    }

}
