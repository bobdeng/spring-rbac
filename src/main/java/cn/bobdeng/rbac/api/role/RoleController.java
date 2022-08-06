package cn.bobdeng.rbac.api.role;

import cn.bobdeng.rbac.domain.Role;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.security.Permission;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class RoleController {
    private final TenantRepository tenantRepository;

    public RoleController(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @GetMapping("/tenants/{id}/roles")
    public List<Role> listRoles(@PathVariable int id) {
        return tenantRepository.findByIdentity(id)
                .map(Tenant::roles)
                .orElse(Collections.emptyList());
    }

    @PostMapping("/tenants/{id}/roles")
    @Permission(allows = "role.create")
    public void newRole(@RequestBody NewRoleForm form, @PathVariable int id) {
        tenantRepository.findByIdentity(id)
                .ifPresent(tenant -> tenant.newRole(form.getDescription()));
    }

    @PatchMapping("/tenants/{tenantId}/roles/{roleId}")
    @Permission(allows = "role.edit")
    public void save(@RequestBody NewRoleForm form, @PathVariable Integer tenantId, @PathVariable Integer roleId) {
        tenantRepository.findByIdentity(tenantId)
                .ifPresent(tenant -> tenant.saveRole(new Role(roleId, form.getDescription())));
    }

    @DeleteMapping("/tenants/{tenantId}/roles/{roleId}")
    @Permission(allows = "role.del")
    public void delete(@PathVariable Integer tenantId, @PathVariable Integer roleId) {
        tenantRepository.findByIdentity(tenantId)
                .ifPresent(tenant -> tenant.deleteRole(roleId));
    }

}
