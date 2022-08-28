package cn.bobdeng.rbac.server.dao;

import cn.bobdeng.rbac.domain.organization.Employee;
import cn.bobdeng.rbac.domain.organization.EmployeeDescription;
import cn.bobdeng.rbac.domain.organization.Organization;
import cn.bobdeng.rbac.domain.organization.OrganizationDescription;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_rbac_employee")
public class EmployeeDO {
    @Id
    @Getter
    private Integer id;
    private Integer organizationId;

    public Employee toEntity(RbacContext.Users users) {
        Employee employee = new Employee(id, new EmployeeDescription());
        Organization organization = new Organization(new OrganizationDescription("部门1", 1));
        employee.setOrganization(() -> organization);
        employee.setUser(() -> users.findByIdentity(id).orElse(null));
        return employee;
    }
}
