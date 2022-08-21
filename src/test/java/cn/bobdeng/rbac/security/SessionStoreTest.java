package cn.bobdeng.rbac.security;

import cn.bobdeng.rbac.api.AdminToken;
import cn.bobdeng.rbac.api.UserToken;
import cn.bobdeng.rbac.domain.TenantRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SessionStoreTest {
    @Test
    public void should_merge_when_session_exist() {
        TenantRepository tenantRepository = mock(TenantRepository.class);
        SessionStore sessionStore = new SessionStore();
        sessionStore.init();
        sessionStore.set(new Session(new AdminToken()));
        sessionStore.set(new Session(new UserToken(1, 1), tenantRepository));
        Session session = sessionStore.get().get();
        assertTrue(session.isAdmin());
        assertNotNull(session.userToken());
    }
}