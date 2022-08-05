package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.security.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TenantController {
    private final TenantRepository tenantRepository;

    public TenantController(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }


    @PostMapping("/tenants")
    public String newTenant(@ModelAttribute("newTenantForm") NewTenantForm form) {
        Tenant tenant = new Tenants(tenantRepository).add(new TenantDescription(form.getName()));
        return "redirect:/rbac/admin/console/domains?tenantId=" + tenant.identity();
    }

    @GetMapping("/tenants")
    @Admin
    public List<Tenant> listTenants(@RequestParam(value = "name", required = false) String name) {
        final Page<Tenant> tenants = tenantRepository.findByName(name, 0, 100);
        return tenants.getElements();
    }
}
