package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.domain.TenantDescription;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.Tenants;
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
        new Tenants(tenantRepository).add(new TenantDescription(form.getName()));
        return "redirect:/rbac/admin/console/tenants";
    }

    @GetMapping("/tenants")
    public String listTenants() {
        return "admin/tenant/list";
    }
}
