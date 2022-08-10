package cn.bobdeng.rbac.api.organization;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.domain.UserDescription;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class EmployeeController {
    @GetMapping("/organizations/{organizationId}/employees")
    public List<User> listEmployees(@PathVariable Integer organizationId,
                                    @RequestAttribute("tenant") Tenant tenant) {
        List<User> result = tenant.organizations().findByIdentity(organizationId)
                .map(organization -> organization.employees().list())
                .orElseGet(Stream::empty)
                .collect(Collectors.toList());
        return result;
    }
}
