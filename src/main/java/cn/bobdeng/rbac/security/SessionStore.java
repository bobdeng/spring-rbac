package cn.bobdeng.rbac.security;

import cn.bobdeng.rbac.domain.Tenant;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class SessionStore {
    private ThreadLocal<Session> sessionThreadLocal;
    private ThreadLocal<Tenant> tenantThreadLocal;

    @PostConstruct
    public void init() {
        sessionThreadLocal = new ThreadLocal<>();
        tenantThreadLocal = new ThreadLocal<>();
    }

    public Optional<Session> get() {
        return Optional.ofNullable(sessionThreadLocal.get());
    }

    public void set(Session session) {
        if (sessionThreadLocal.get() != null) {
            session = session.merge(sessionThreadLocal.get());
        }
        sessionThreadLocal.set(session);
    }

    public void setTenant(Tenant tenant) {
        this.tenantThreadLocal.set(tenant);
    }

    public Tenant getTenant() {
        return this.tenantThreadLocal.get();
    }

    public void clear() {
        sessionThreadLocal.remove();
        tenantThreadLocal.remove();
    }
}
