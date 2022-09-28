package cn.bobdeng.rbac.server.dao;

import cn.bobdeng.rbac.domain.organization.*;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.function.Function;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_rbac_employee")
public class EmployeeDO {
    @Id
    @Getter
    private Integer id;
    private Integer tenantId;
    private Integer organizationId;

    public Employee toEntity(RbacContext.Users users, Function<Integer, Organization> organizationFetcher) {
        Employee employee = new Employee(id, new EmployeeDescription());
        employee.setOrganization(() -> organizationFetcher.apply(organizationId));
        employee.setUser(() -> users.findByIdentity(id).orElse(null));
        return employee;
    }
}
