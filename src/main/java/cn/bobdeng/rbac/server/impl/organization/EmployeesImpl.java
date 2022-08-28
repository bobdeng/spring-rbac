package cn.bobdeng.rbac.server.impl.organization;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.organization.Employee;
import cn.bobdeng.rbac.domain.organization.OrganizationContext;
import cn.bobdeng.rbac.server.dao.EmployeeDAO;
import cn.bobdeng.rbac.server.dao.EmployeeDO;

import java.util.Optional;

public class EmployeesImpl implements OrganizationContext.Employees {
    private EmployeeDAO employeeDAO;

    public EmployeesImpl(EmployeeDAO employeeDAO) {

        this.employeeDAO = employeeDAO;
    }

    @Override
    public Optional<Employee> findByIdentity(Integer integer) {
        return employeeDAO.findById(integer).map(EmployeeDO::toEntity);
    }
}
