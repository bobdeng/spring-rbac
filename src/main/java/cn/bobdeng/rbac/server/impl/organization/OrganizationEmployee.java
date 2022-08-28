package cn.bobdeng.rbac.server.impl.organization;

import cn.bobdeng.rbac.domain.organization.Employee;
import cn.bobdeng.rbac.domain.rbac.User;
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

    public OrganizationEmployee(Organization organization, EmployeeDAO employeeDAO, RbacContext rbacContext) {
        this.organization = organization;
        this.employeeDAO = employeeDAO;
        this.rbacContext = rbacContext;
    }

    @Override
    public Stream<Employee> list() {
        return employeeDAO.findAllByOrganizationId(organization.identity())
                .stream()
                .map(employeeDO -> employeeDO.toEntity(getUsers()));
    }

    private RbacContext.Users getUsers() {
        return rbacContext.users(organization.tenant());
    }

    @Override
    public Optional<Employee> findByIdentity(Integer integer) {
        return employeeDAO.findById(integer)
                .map(employeeDO -> employeeDO.toEntity(getUsers()));
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
