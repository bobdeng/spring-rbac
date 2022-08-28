package cn.bobdeng.rbac.security;

import cn.bobdeng.rbac.domain.cbac.CbacContext;
import cn.bobdeng.rbac.domain.cbac.ContextAuthority;
import cn.bobdeng.rbac.domain.cbac.ContextObject;
import cn.bobdeng.rbac.domain.organization.Employee;
import cn.bobdeng.rbac.domain.organization.Organization;
import cn.bobdeng.rbac.domain.organization.OrganizationContext;
import cn.bobdeng.rbac.domain.organization.OrganizationDescription;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.domain.rbac.Role;
import cn.bobdeng.rbac.domain.rbac.RoleDescription;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Aspect
public class ObjectPermissionCheckAspect {
    private final SessionStore sessionStore;
    private final CbacContext cbacContext;
    private final RbacContext rbacContext;
    private final OrganizationContext organizationContext;

    public ObjectPermissionCheckAspect(SessionStore sessionStore, CbacContext cbacContext, RbacContext rbacContext, OrganizationContext organizationContext) {
        this.sessionStore = sessionStore;
        this.cbacContext = cbacContext;
        this.rbacContext = rbacContext;
        this.organizationContext = organizationContext;
    }

    @Before("@annotation(permission)")
    public void before(JoinPoint joinPoint, ObjectPermission permission) {
        if (sessionStore.get().isEmpty()) {
            throw new PermissionDeniedException();
        }
        Session session = sessionStore.get().get();
        CbacContext.Cbac cbac = cbacContext.asCbac(sessionStore.getTenant());
        Object id = AspectUtils.getExpressValue(joinPoint, permission.id());
        ContextObject object = new ContextObject(permission.type(), id.toString());
        List<String> roleNames = rbacContext.userRoles(session.user())
                .list().map(Role::description)
                .map(RoleDescription::getName)
                .toList();
        String[] organizations = organizationContext.asEmployees(sessionStore.getTenant())
                .findByIdentity(session.userId())
                .map(Employee::organization)
                .map(Organization::getDescription)
                .map(OrganizationDescription::getName)
                .stream().toArray(String[]::new);
        ContextAuthority authority = ContextAuthority.builder()
                .withUser(session.userId())
                .withRoles(roleNames.toArray(String[]::new))
                .withOrganizations(organizations)
                .build();
        if (!cbac.isAllowed(object, authority)) {
            throw new PermissionDeniedException();
        }
    }
}
