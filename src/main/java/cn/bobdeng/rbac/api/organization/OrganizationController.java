package cn.bobdeng.rbac.api.organization;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.organization.OrganizationContext;
import cn.bobdeng.rbac.domain.organization.Organization;
import cn.bobdeng.rbac.domain.organization.OrganizationDescription;
import cn.bobdeng.rbac.security.Permission;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class OrganizationController {
    private final OrganizationContext organizationContext;

    public OrganizationController(OrganizationContext organizationContext) {
        this.organizationContext = organizationContext;
    }

    @GetMapping("/api/1.0/organizations")
    @Transactional
    public List<Organization> list(@RequestAttribute("tenant") Tenant tenant) {
        return organizationContext.asOrganization(tenant).all();
    }

    @PostMapping("/api/1.0/organizations")
    @Transactional
    @Permission(allows = "organization.create")
    public void create(@RequestAttribute("tenant") Tenant tenant,
                       @RequestBody NewOrganizationForm form) {
        organizationContext.asOrganization(tenant).add(new OrganizationDescription(form.getName(), form.getParent()));
    }
}
