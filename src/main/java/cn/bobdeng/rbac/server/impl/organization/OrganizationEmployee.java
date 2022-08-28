package cn.bobdeng.rbac.server.impl.organization;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.organization.Employee;
import cn.bobdeng.rbac.domain.organization.OrganizationContext;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.domain.organization.Organization;
import cn.bobdeng.rbac.server.dao.EmployeeDAO;
import cn.bobdeng.rbac.server.dao.EmployeeDO;

import java.util.Optional;
import java.util.stream.Stream;

public class OrganizationEmployee implements Organization.Employees {
    private final Organization organization;
    private final EmployeeDAO employeeDAO;
    private final RbacContext rbacContext;
    private final Tenant tenant;
    private OrganizationContext organizationContext;

    public OrganizationEmployee(Organization organization, EmployeeDAO employeeDAO, RbacContext rbacContext, OrganizationContext organizationContext) {
        this.organization = organization;
        this.employeeDAO = employeeDAO;
        this.rbacContext = rbacContext;
        this.organizationContext = organizationContext;
        this.tenant = organization.tenant();
    }

    @Override
    public Stream<Employee> list() {
        return employeeDAO.findAllByOrganizationId(organization.identity())
                .stream()
                .map(employeeDO -> employeeDO.toEntity(getUsers(), (id) -> organization));
    }

    private OrganizationContext.Organizations getOrganizations() {
        return organizationContext.asOrganization(tenant).organizations();
    }

    private RbacContext.Users getUsers() {
        return rbacContext.users(organization.tenant());
    }

    @Override
    public Optional<Employee> findByIdentity(Integer integer) {
        return employeeDAO.findById(integer)
                .map(employeeDO -> employeeDO.toEntity(getUsers(), (id) -> organization));
    }

    @Override
    public Employee save(Employee employee) {
        employeeDAO.save(EmployeeDO.builder()
                .organizationId(organization.identity())
                .id(employee.identity())
                .build());
        return employee;
    }

    @Override
    public void delete(Employee employee) {
        employeeDAO.deleteById(employee.identity());
    }
}
