package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rbac/admin/console")
public class TenantController {
    private final TenantRepository tenantRepository;

    public TenantController(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @GetMapping("/new_tenant")
    public String newTenantPage(Model model) {
        model.addAttribute("newTenantForm", new NewTenantForm());
        return "admin/tenant/new";
    }

    @PostMapping("/tenants")
    public String newTenant(@ModelAttribute("newTenantForm") NewTenantForm form) {
        Tenant tenant = new Tenants(tenantRepository).add(new TenantDescription(form.getName()));
        return "redirect:/rbac/admin/console/domains?tenantId=" + tenant.identity();
    }

    @GetMapping("/tenants")
    public String listTenants(Model model, @ModelAttribute("searchForm") SearchTenantForm form) {
        final Page<Tenant> tenants = tenantRepository.findByName(form.getName(), 0, 100);
        model.addAttribute("tenants", tenants);
        return "admin/tenant/list";
    }
}
