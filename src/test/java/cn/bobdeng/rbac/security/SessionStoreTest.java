package cn.bobdeng.rbac.security;

import cn.bobdeng.rbac.api.AdminToken;
import cn.bobdeng.rbac.api.UserToken;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionStoreTest {
    @Test
    public void should_merge_when_session_exist(){
        SessionStore sessionStore = new SessionStore();
        sessionStore.init();
        sessionStore.set(new Session(new AdminToken()));
        sessionStore.set(new Session(new UserToken(1,1)));
        Session session = sessionStore.get().get();
        assertTrue(session.isAdmin());
        assertNotNull(session.userToken());
    }
}