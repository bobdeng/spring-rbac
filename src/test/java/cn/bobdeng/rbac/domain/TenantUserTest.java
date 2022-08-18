package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.domain.rbac.RbacImpl;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TenantUserTest {

    @Test
    public void add_user_to_tenant() {
        RbacContext.Users users = mock(RbacContext.Users.class);
        UserDescription userDescription = new UserDescription("bob");
        RbacImpl rbac = new RbacImpl(users, null, null, null);

        rbac.addUser(userDescription);

        verify(users).save(new User(userDescription));
    }

}
