package cn.bobdeng.rbac.server.impl.organization;

import cn.bobdeng.rbac.domain.rbac.User;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.domain.organization.Organization;
import cn.bobdeng.rbac.server.dao.EmployeeDAO;

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
    public Stream<User> list() {
        return employeeDAO.findAllByOrganizationId(organization.identity())
                .stream()
                .flatMap(employeeDO -> getUsers().findByIdentity(employeeDO.getId()).stream());
    }

    private RbacContext.Users getUsers() {
        return rbacContext.users(organization.tenant());
    }

    @Override
    public Optional<User> findByIdentity(Integer integer) {
        return employeeDAO.findById(integer)
                .flatMap(employeeDO -> getUsers().findByIdentity(employeeDO.getId()));
    }


    @Override
    public void delete(User user) {
        employeeDAO.deleteById(user.identity());
    }
}
