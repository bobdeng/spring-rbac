package cn.bobdeng.rbac.security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class PermissionCheckAspect {
    private final SessionStore sessionStore;

    public PermissionCheckAspect(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Before("@annotation(permission)")
    public void before(Permission permission) {
        Session session = sessionStore.get().orElseThrow(PermissionDeniedException::new);
        if (!session.hasPermission(permission.allows())) {
            throw new PermissionDeniedException();
        }
    }
}
