package cn.bobdeng.rbac.security;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class AdminCheckAspect {
    private final SessionStore sessionStore;

    public AdminCheckAspect(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Before("@annotation(admin)")
    public void before(JoinPoint joinPoint, Admin admin) {
        sessionStore.get()
                .filter(Session::isAdmin)
                .orElseThrow(PermissionDeniedException::new);
    }
}
