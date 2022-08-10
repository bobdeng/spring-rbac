package cn.bobdeng.rbac.api.organization;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.security.Permission;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class EmployeeController {
    @GetMapping("/organizations/{organizationId}/employees")
    public List<User> listEmployees(@PathVariable Integer organizationId,
                                    @RequestAttribute("tenant") Tenant tenant) {
        return tenant.organizations().findByIdentity(organizationId)
                .map(organization -> organization.employees().list())
                .orElseGet(Stream::empty)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/organizations/{organizationId}/employees/{userId}")
    @Permission(allows = "organization.employee")
    @Transactional
    public void removeEmployee(@PathVariable Integer organizationId, @PathVariable Integer userId,
                               @RequestAttribute("tenant") Tenant tenant) {
        tenant.organizations().findByIdentity(organizationId)
                .ifPresent(organization -> organization.removeEmployee(userId));
    }
}
