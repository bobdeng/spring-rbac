package cn.bobdeng.rbac.api.domain;

import cn.bobdeng.rbac.domain.Domain;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Controller
public class DomainController {
    private final TenantRepository tenantRepository;

    public DomainController(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @GetMapping("/rbac/admin/console/tenant/{id}/domains")
    @Transactional
    public String listDomain(@PathVariable("id") int tenantId, Model model) {
        List<Domain> domains = tenantRepository.findByIdentity(tenantId)
                .map(Tenant::domains)
                .orElseGet(Collections::emptyList);
        model.addAttribute("domains", domains);
        return "admin/tenant/domain/list";
    }
}
