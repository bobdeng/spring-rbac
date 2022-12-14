package cn.bobdeng.rbac.api.role;

import cn.bobdeng.rbac.api.ObjectNotFoundException;
import cn.bobdeng.rbac.api.user.RbacController;
import cn.bobdeng.rbac.domain.rbac.Role;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.security.Admin;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@RestController
public class RoleController extends RbacController {

    public RoleController(TenantRepository tenantRepository, RbacContext rbacContext) {
        super(tenantRepository, rbacContext);
    }

    @GetMapping("/api/1.0/tenants/{id}/roles")
    @Admin
    public List<Role> listRolesOfTenant(@PathVariable int id) {
        return tenantRepository.findByIdentity(id)
                .map(this::getRbac)
                .map(RbacContext.Rbac::listRoles)
                .orElse(Collections.emptyList());
    }

    @GetMapping("/api/1.0/roles")
    public List<Role> listRoles(@RequestAttribute("tenant") Tenant tenant) {
        return getRbac(tenant).listRoles();
    }

    @PostMapping("/api/1.0/tenants/{id}/roles")
    @Admin
    public void newRole(@RequestBody NewRoleForm form, @PathVariable int id) {
        tenantRepository.findByIdentity(id)
                .ifPresent(tenant -> getRbac(tenant).newRole(form.getDescription()));
    }

    @PatchMapping("/api/1.0/tenants/{tenantId}/roles/{roleId}")
    @Admin
    public void save(@RequestBody NewRoleForm form, @PathVariable Integer tenantId, @PathVariable Integer roleId) {
        tenantRepository.findByIdentity(tenantId)
                .ifPresent(tenant -> getRbac(tenant).saveRole(new Role(roleId, form.getDescription())));
    }

    @DeleteMapping("/api/1.0/tenants/{tenantId}/roles/{roleId}")
    @Admin
    @Transactional
    public void delete(@PathVariable Integer tenantId, @PathVariable Integer roleId) {
        tenantRepository.findByIdentity(tenantId)
                .ifPresent(tenant -> getRbac(tenant).deleteRole(roleId));
    }

    @GetMapping("/api/1.0/tenants/{tenantId}/roles/{roleId}")
    @Admin
    public Role get(@PathVariable Integer tenantId, @PathVariable Integer roleId) {
        return tenantRepository.findByIdentity(tenantId)
                .flatMap(tenant -> getRbac(tenant).getRole(roleId))
                .orElseThrow(ObjectNotFoundException::new);
    }

}
