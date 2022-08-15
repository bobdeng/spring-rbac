package cn.bobdeng.rbac.api.role;

import cn.bobdeng.rbac.api.ObjectNotFoundException;
import cn.bobdeng.rbac.domain.Role;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.security.Admin;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@RestController
public class RoleController {
    private final TenantRepository tenantRepository;

    public RoleController(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @GetMapping("/tenants/{id}/roles")
    @Admin
    public List<Role> listRolesOfTenant(@PathVariable int id) {
        return tenantRepository.findByIdentity(id)
                .map(Tenant::roles)
                .orElse(Collections.emptyList());
    }

    @GetMapping("/roles")
    public List<Role> listRoles(@RequestAttribute("tenant") Tenant tenant) {
        return tenant.roles();
    }

    @PostMapping("/tenants/{id}/roles")
    @Admin
    public void newRole(@RequestBody NewRoleForm form, @PathVariable int id) {
        tenantRepository.findByIdentity(id)
                .ifPresent(tenant -> tenant.newRole(form.getDescription()));
    }

    @PatchMapping("/tenants/{tenantId}/roles/{roleId}")
    @Admin
    public void save(@RequestBody NewRoleForm form, @PathVariable Integer tenantId, @PathVariable Integer roleId) {
        tenantRepository.findByIdentity(tenantId)
                .ifPresent(tenant -> tenant.saveRole(new Role(roleId, form.getDescription())));
    }

    @DeleteMapping("/tenants/{tenantId}/roles/{roleId}")
    @Admin
    @Transactional
    public void delete(@PathVariable Integer tenantId, @PathVariable Integer roleId) {
        tenantRepository.findByIdentity(tenantId)
                .ifPresent(tenant -> tenant.deleteRole(roleId));
    }

    @GetMapping("/tenants/{tenantId}/roles/{roleId}")
    @Admin
    public Role get(@PathVariable Integer tenantId, @PathVariable Integer roleId) {
        return tenantRepository.findByIdentity(tenantId)
                .flatMap(tenant -> tenant.getRole(roleId))
                .orElseThrow(ObjectNotFoundException::new);
    }

}
