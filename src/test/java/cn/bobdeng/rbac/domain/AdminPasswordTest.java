package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.domain.rbac.AdminPassword;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AdminPasswordTest {
    @Test
    public void should_send_password_when_verify_fail() {
        AdminPassword.Notifier notifier = mock(AdminPassword.Notifier.class);
        AdminPassword.Store store = mock(AdminPassword.Store.class);
        when(store.get()).thenReturn(Optional.ofNullable(new BCryptPasswordEncoder().encode("123456")));
        AdminPassword adminPassword = new AdminPassword(notifier, store);

        assertFalse(adminPassword.verify("123"));

        verify(notifier).notify(anyString());
        verify(store).save(anyString());
    }

    @Test
    public void should_send_password_when_no_password() {

        AdminPassword.Notifier notifier = mock(AdminPassword.Notifier.class);
        AdminPassword.Store store = mock(AdminPassword.Store.class);
        when(store.get()).thenReturn(Optional.empty());
        AdminPassword adminPassword = new AdminPassword(notifier, store);

        assertFalse(adminPassword.verify("123"));

        verify(notifier).notify(anyString());
        verify(store).save(anyString());
    }

    @Test
    public void should_success_when_math() {
        AdminPassword.Notifier notifier = mock(AdminPassword.Notifier.class);
        AdminPassword.Store store = mock(AdminPassword.Store.class);
        when(store.get()).thenReturn(Optional.ofNullable(new BCryptPasswordEncoder().encode("123456")));
        AdminPassword adminPassword = new AdminPassword(notifier, store);

        assertTrue(adminPassword.verify("123456"));

        verify(notifier, never()).notify(anyString());
        verify(store, never()).save(anyString());
    }
}