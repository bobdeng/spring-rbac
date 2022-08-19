package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.domain.rbac.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TenantLoginNamesTest {
    @Test
    public void save_account() {
        User user = new User(101, null);
        RbacContext.LoginNames loginNames = mock(RbacContext.LoginNames.class);
        when(loginNames.save(any(LoginName.class))).thenReturn(
                new LoginName(1, new LoginNameDescription("bob", user.identity()))
        );
        LoginNameDescription description = new LoginNameDescription("bob", user.identity());
        RbacContext rbacContext = mock(RbacContext.class);
        Tenant tenant = new Tenant();
        RbacImpl rbac = new RbacImpl(tenant, rbacContext);
        when(rbacContext.loginNames(tenant)).thenReturn(loginNames);

        LoginName result = rbac.addLoginName(description);

        verify(loginNames).save(new LoginName(description));
    }

    @Test
    public void throw_when_login_name_exist() {
        User user = new User(101, null);
        RbacContext.LoginNames loginNames = mock(RbacContext.LoginNames.class);
        when(loginNames.findByLoginName("bob")).thenReturn(Optional.of(new LoginName()));
        LoginNameDescription description = new LoginNameDescription("bob", user.identity());
        RbacContext rbacContext = mock(RbacContext.class);
        Tenant tenant = new Tenant();
        RbacImpl rbac = new RbacImpl(tenant, rbacContext);
        when(rbacContext.loginNames(tenant)).thenReturn(loginNames);

        assertThrows(DuplicateLoginNameException.class, () -> rbac.addLoginName(description));

    }

}
