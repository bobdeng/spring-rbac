package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.domain.config.*;
import cn.bobdeng.rbac.domain.rbac.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserPasswordTest {
    private RbacContext rbacContext;
    private User.UserPassword userPassword;
    private User user;

    @BeforeEach
    public void setup() {
        rbacContext = mock(RbacContext.class);
        userPassword = mock(User.UserPassword.class);
        user = new User(1, new UserDescription("", User.UserStatus.Normal));
        user.setRbacContext(rbacContext);
        user.setTenant(Tenant::new);
        ConfigurationContext configurationContext = mock(ConfigurationContext.class);
        Parameters parameters = mock(Parameters.class);
        user.setConfigurationContext(configurationContext);
        User.UserLock userLock = mock(User.UserLock.class);
        when(parameters.findByIdentity(BaseParameters.PASSWORD_POLICY)).thenReturn(Optional.of(new Parameter("", new ParameterDescription("", "none"))));
        when(configurationContext.parameters(any())).thenReturn(parameters);
        when(rbacContext.userLock(user)).thenReturn(userLock);
        when(userLock.findByIdentity(user.identity())).thenReturn(Optional.empty());
        when(rbacContext.userPassword(user)).thenReturn(userPassword);
    }

    @Test
    public void save_user_password() {
        when(userPassword.encodePassword("123456")).thenReturn("654321");
        user.savePassword(new RawPassword("123456"));

        Password password = new Password(1, new PasswordDescription("654321"));
        verify(userPassword).save(password);
    }

    @Test
    public void should_return_false_when_user_password_not_exist() {
        when(userPassword.findByIdentity(1)).thenReturn(Optional.empty());
        assertFalse(user.verifyPassword("123456"));
    }

    @Test
    public void verify_user_password() {
        PasswordDescription description = new PasswordDescription("654321");
        when(userPassword.findByIdentity(1)).thenReturn(Optional.of(new Password(1, description)));
        when(userPassword.match("123456","654321")).thenReturn(true);
        assertTrue(user.verifyPassword("123456"));
        when(userPassword.match("123455","654321")).thenReturn(false);
        assertFalse(user.verifyPassword("123455"));
    }
}
