package cn.bobdeng.rbac.api.role;

import cn.bobdeng.rbac.domain.Role;
import cn.bobdeng.rbac.domain.RoleDescription;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
    public void newRole(@RequestBody NewRoleForm form,@PathVariable int id){
        tenantRepository.findByIdentity(id)
                .ifPresent(tenant -> {
                    tenant.newRole(new RoleDescription(form.getName(),form.getAllows()));
                });
    }
}
