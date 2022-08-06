package cn.bobdeng.rbac.api.domain;

import cn.bobdeng.rbac.domain.Domain;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class TenantDomainController {
    private final TenantRepository tenantRepository;

    public TenantDomainController(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @GetMapping("/tenants/{id}/domains")
    public List<Domain> listDomainOfTenant(@PathVariable int id) {
        return tenantRepository.findByIdentity(id)
                .map(Tenant::domains)
                .orElse(Collections.emptyList());
    }
}
