package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.archtype.LoginNameDescription;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TenantLoginNamesTest {
    @Test
    public void save_account() {
        Tenant tenant = new Tenant(100, null);
        User user = new User(101, null);
        Tenant.LoginNames loginNames = mock(Tenant.LoginNames.class);
        when(loginNames.save(any(LoginName.class))).thenReturn(
                new LoginName(1, new LoginNameDescription("bob", user.identity()))
        );
        tenant.setLoginNames(loginNames);
        LoginNameDescription description = new LoginNameDescription("bob", user.identity());

        LoginName result = tenant.addLoginName(description);

        verify(loginNames).save(new LoginName(description));
    }

    @Test
    public void throw_when_login_name_exist() {
        Tenant tenant = new Tenant(100, null);
        User user = new User(101, null);
        Tenant.LoginNames loginNames = mock(Tenant.LoginNames.class);
        when(loginNames.findByLoginName("bob")).thenReturn(Optional.of(new LoginName()));
        tenant.setLoginNames(loginNames);
        LoginNameDescription description = new LoginNameDescription("bob", user.identity());

        assertThrows(DuplicateLoginNameException.class, () -> tenant.addLoginName(description));

    }

}
