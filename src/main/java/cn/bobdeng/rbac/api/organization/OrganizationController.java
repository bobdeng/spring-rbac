package cn.bobdeng.rbac.api.organization;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.tenant.organization.Organization;
import cn.bobdeng.rbac.domain.tenant.organization.OrganizationDescription;
import cn.bobdeng.rbac.security.Permission;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrganizationController {
    @GetMapping("/organizations")
    @Transactional
    public List<Organization> list(@RequestAttribute("tenant") Tenant tenant) {
        return tenant.organizations().list().collect(Collectors.toList());
    }

    @PostMapping("/organizations")
    @Transactional
    @Permission(allows = "organization.create")
    public void create(@RequestAttribute("tenant") Tenant tenant,
                       @RequestBody NewOrganizationForm form) {
        tenant.newOrganization(new OrganizationDescription(form.getName(), form.getParent()));
    }
}
