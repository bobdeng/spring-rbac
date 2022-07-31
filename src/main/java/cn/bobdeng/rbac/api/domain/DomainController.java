package cn.bobdeng.rbac.api.domain;

import cn.bobdeng.rbac.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Controller
public class DomainController {
    private final TenantRepository tenantRepository;
    private final DomainRepository domainRepository;

    public DomainController(TenantRepository tenantRepository, DomainRepository domainRepository) {
        this.tenantRepository = tenantRepository;
        this.domainRepository = domainRepository;
    }

    @GetMapping("/rbac/admin/console/domains")
    @Transactional
    public String listDomain(HttpServletRequest request, Model model) {
        Integer tenantId = Integer.parseInt(request.getParameter("tenantId"));
        List<Domain> domains = tenantRepository.findByIdentity(tenantId)
                .map(Tenant::domains)
                .orElseGet(Collections::emptyList);
        model.addAttribute("domains", domains);
        model.addAttribute("tenant", tenantRepository.findById(tenantId).orElse(null));
        return "admin/tenant/domain/list";
    }

    @GetMapping("/rbac/admin/console/domain/new_domain")
    @Transactional
    public String newDomainPage(HttpServletRequest request, Model model) {
        NewDomainForm form = new NewDomainForm();
        form.setTenantId(Integer.parseInt(request.getParameter("tenantId")));
        model.addAttribute("newDomainForm", form);
        return "admin/tenant/domain/new";
    }

    @PostMapping("/rbac/admin/console/domains")
    @Transactional
    public String newDomain(@ModelAttribute("newDomainForm") NewDomainForm form) {
        new Domains(domainRepository).newDomain(new DomainDescription(form.getDomain(), form.getTenantId()));
        return "redirect:/rbac/admin/console/domains?tenantId=" + form.getTenantId();
    }
}
