package cn.bobdeng.rbac.security;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class SessionStore {
    private ThreadLocal<Session> sessionThreadLocal;

    @PostConstruct
    private void init() {
        sessionThreadLocal = new ThreadLocal<>();
    }

    public Optional<Session> get() {
        return Optional.ofNullable(sessionThreadLocal.get());
    }

    public void set(Session session) {
        sessionThreadLocal.set(session);
    }

    public void clear() {
        sessionThreadLocal.remove();
    }
}
