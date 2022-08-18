package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.domain.rbac.RbacContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserPasswordTest {
    private RbacContext rbacContext;

    @BeforeEach
    public void setup(){
        rbacContext= mock(RbacContext.class);
    }
    @Test
    public void save_user_password() {
        User.UserPassword userPassword = mock(User.UserPassword.class);
        when(userPassword.encodePassword("123456")).thenReturn("654321");
        User user = new User(1, null);
        user.setRbacContext(rbacContext);
        when(rbacContext.userPassword(user)).thenReturn(userPassword);
        user.savePassword(new RawPassword("123456"));

        Password password = new Password(1, new PasswordDescription("654321"));
        verify(userPassword).save(password);
    }

    @Test
    public void should_return_false_when_user_password_not_exist() {
        User.UserPassword userPassword = mock(User.UserPassword.class);
        when(userPassword.match("123456", "654321")).thenReturn(true);
        PasswordDescription description = new PasswordDescription("654321");
        when(userPassword.findByIdentity(1)).thenReturn(Optional.empty());
        User user = new User(1, null);
        user.setRbacContext(rbacContext);
        when(rbacContext.userPassword(user)).thenReturn(userPassword);
        assertFalse(user.verifyPassword("123456"));
    }

    @Test
    public void verify_user_password() {
        User.UserPassword userPassword = mock(User.UserPassword.class);
        when(userPassword.match("123456", "654321")).thenReturn(true);
        PasswordDescription description = new PasswordDescription("654321");
        when(userPassword.findByIdentity(1)).thenReturn(Optional.of(new Password(1, description)));
        User user = new User(1, null);
        user.setRbacContext(rbacContext);
        when(rbacContext.userPassword(user)).thenReturn(userPassword);
        assertTrue(user.verifyPassword("123456"));
        assertFalse(user.verifyPassword("123455"));
    }
}
