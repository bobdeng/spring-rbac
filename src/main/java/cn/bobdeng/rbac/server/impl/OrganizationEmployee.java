package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.domain.tenant.organization.Organization;
import cn.bobdeng.rbac.server.dao.EmployeeDAO;
import cn.bobdeng.rbac.server.dao.UserDAO;

import java.util.Optional;
import java.util.stream.Stream;

public class OrganizationEmployee implements Organization.Employees {
    private final Organization organization;
    private final EmployeeDAO employeeDAO;
    private final TenantRepository tenantRepository;
    private final UserDAO userDAO;
    private final RbacContext rbacContext;
    public OrganizationEmployee(Organization organization, EmployeeDAO employeeDAO, TenantRepository tenantRepository, UserDAO userDAO, RbacContext rbacContext) {
        this.organization = organization;
        this.employeeDAO = employeeDAO;
        this.tenantRepository = tenantRepository;
        this.userDAO = userDAO;
        this.rbacContext = rbacContext;
    }

    @Override
    public Stream<User> list() {
        return employeeDAO.findAllByOrganizationId(organization.identity())
                .stream()
                .flatMap(employeeDO -> userDAO.findById(employeeDO.getId()).map(Stream::of).orElse(Stream.empty()))
                .map(userDO -> userDO.toUser(tenantRepository, rbacContext));
    }

    @Override
    public Optional<User> findByIdentity(Integer integer) {
        return employeeDAO.findById(integer)
                .flatMap(employeeDO -> userDAO.findById(integer))
                .map(userDO -> userDO.toUser(tenantRepository, rbacContext));
    }


    @Override
    public void delete(User user) {
        employeeDAO.deleteById(user.identity());
    }
}
