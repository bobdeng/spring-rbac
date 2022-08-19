package cn.bobdeng.rbac.domain.rbac;

import cn.bobdeng.rbac.archtype.FieldIllegalException;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.config.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTest {
    private Tenant tenant;
    private User user;
    private RbacContext rbacContext;
    private User.UserPassword userPassword;
    private ConfigurationContext configurationContext;
    private Parameters parameters;

    @BeforeEach
    public void setup() {
        tenant = new Tenant();
        user = new User(1, null);
        user.setTenant(() -> tenant);
        rbacContext = mock(RbacContext.class);
        userPassword = mock(User.UserPassword.class);
        user.setRbacContext(rbacContext);
        configurationContext = mock(ConfigurationContext.class);
        user.setConfigurationContext(configurationContext);
        parameters = mock(Parameters.class);
    }

    @Test
    public void should_not_check_password_when_policy_is_none() {
        when(parameters.findByIdentity(BaseParameters.PASSWORD_POLICY))
                .thenReturn(Optional.of(new Parameter("", new ParameterDescription("", "none"))));
        when(configurationContext.parameters(tenant)).thenReturn(parameters);
        when(rbacContext.userPassword(user)).thenReturn(userPassword);
        when(userPassword.encodePassword("123456")).thenReturn("654321");

        user.savePassword(new RawPassword("123456"));

        verify(userPassword).save(new Password(user.identity(), new PasswordDescription("654321")));

    }

    @Test
    public void should_weak_check_password_when_policy_is_weak() {
        when(parameters.findByIdentity(BaseParameters.PASSWORD_POLICY))
                .thenReturn(Optional.of(new Parameter("", new ParameterDescription("", "weak"))));
        when(configurationContext.parameters(tenant)).thenReturn(parameters);
        when(rbacContext.userPassword(user)).thenReturn(userPassword);
        when(userPassword.encodePassword("123456")).thenReturn("654321");

        assertThrows(FieldIllegalException.class, () ->
                user.savePassword(new RawPassword("123456")));

    }
}