package cn.bobdeng.rbac.security;

import cn.bobdeng.rbac.domain.TenantRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class PermissionCheckAspect {
    private final SessionStore sessionStore;
    private final TenantRepository tenantRepository;

    public PermissionCheckAspect(SessionStore sessionStore, TenantRepository tenantRepository) {
        this.sessionStore = sessionStore;
        this.tenantRepository = tenantRepository;
    }

    @Before("@annotation(permission)")
    public void before(Permission permission) {
        Session session = sessionStore.get().orElseThrow(PermissionDeniedException::new);
        if (!session.hasPermission(permission.allows(), tenantRepository)) {
            throw new PermissionDeniedException();
        }
    }
}
