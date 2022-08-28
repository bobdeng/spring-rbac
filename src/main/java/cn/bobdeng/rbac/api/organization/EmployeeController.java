package cn.bobdeng.rbac.api.organization;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.organization.OrganizationContext;
import cn.bobdeng.rbac.domain.rbac.User;
import cn.bobdeng.rbac.security.Permission;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class EmployeeController {
    private final OrganizationContext organizationContext;

    public EmployeeController(OrganizationContext organizationContext) {
        this.organizationContext = organizationContext;
    }

    @GetMapping("/organizations/{organizationId}/employees")
    public List<User> listEmployees(@PathVariable Integer organizationId,
                                    @RequestAttribute("tenant") Tenant tenant) {
        return organizationContext.asOrganization(tenant).organizations().findByIdentity(organizationId)
                .map(organization -> organization.employees().list())
                .orElseGet(Stream::empty)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/organizations/{organizationId}/employees/{userId}")
    @Permission(allows = "organization.employee")
    @Transactional
    public void removeEmployee(@PathVariable Integer organizationId, @PathVariable Integer userId,
                               @RequestAttribute("tenant") Tenant tenant) {
        organizationContext.asOrganization(tenant).organizations().findByIdentity(organizationId)
                .ifPresent(organization -> organization.removeEmployee(userId));
    }
}
