package cn.bobdeng.rbac.server.impl.organization;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.organization.Employee;
import cn.bobdeng.rbac.domain.organization.OrganizationContext;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.server.dao.EmployeeDAO;

import java.util.Optional;

public class EmployeesImpl implements OrganizationContext.Employees {
    private EmployeeDAO employeeDAO;
    private RbacContext rbacContext;
    private Tenant tenant;

    public EmployeesImpl(EmployeeDAO employeeDAO, RbacContext rbacContext, Tenant tenant) {

        this.employeeDAO = employeeDAO;
        this.rbacContext = rbacContext;
        this.tenant = tenant;
    }

    @Override
    public Optional<Employee> findByIdentity(Integer integer) {
        RbacContext.Users users = rbacContext.users(tenant);
        return employeeDAO.findById(integer).map(employeeDO -> employeeDO.toEntity(users));
    }
}
