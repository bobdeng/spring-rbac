package cn.bobdeng.rbac.api.organization;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.organization.OrganizationContext;
import cn.bobdeng.rbac.domain.organization.Organization;
import cn.bobdeng.rbac.domain.organization.OrganizationDescription;
import cn.bobdeng.rbac.security.Permission;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class OrganizationController {
    private final TenantRepository tenantRepository;

    public OrganizationController(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @GetMapping("/organizations")
    @Transactional
    public List<Organization> list(@RequestAttribute("tenant") Tenant tenant) {
        return getOrganizationStructure(tenant).all();
    }

    private OrganizationContext.OrganizationStructure getOrganizationStructure(Tenant tenant) {
        return tenantRepository.organizationContext().asOrganization(tenant);
    }

    @PostMapping("/organizations")
    @Transactional
    @Permission(allows = "organization.create")
    public void create(@RequestAttribute("tenant") Tenant tenant,
                       @RequestBody NewOrganizationForm form) {
        getOrganizationStructure(tenant).add(new OrganizationDescription(form.getName(), form.getParent()));
    }
}
