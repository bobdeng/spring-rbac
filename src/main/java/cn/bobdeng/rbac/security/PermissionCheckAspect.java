package cn.bobdeng.rbac.security;

import cn.bobdeng.rbac.api.UserToken;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.Tenants;
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
    public void before(JoinPoint joinPoint, Permission permission) {
        Session session = sessionStore.get().orElseThrow(PermissionDeniedException::new);
        if (session.isAdmin()) {
            return;
        }
        UserToken userToken = session.userToken();
        tenantRepository.findByIdentity(userToken.getTenant())
                .flatMap(tenant -> tenant.users().findByIdentity(userToken.getId()))
                .filter(user -> user.hasSomePermission(permission.allows()))
                .orElseThrow(PermissionDeniedException::new);
    }
}
