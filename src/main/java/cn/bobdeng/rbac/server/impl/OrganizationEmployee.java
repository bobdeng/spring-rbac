package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.domain.tenant.organization.Organization;
import cn.bobdeng.rbac.server.dao.EmployeeDAO;
import cn.bobdeng.rbac.server.dao.UserDAO;
import cn.bobdeng.rbac.server.dao.UserDO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class OrganizationEmployee implements Organization.Employees {
    private final Organization organization;
    private final EmployeeDAO employeeDAO;
    private final TenantRepository tenantRepository;
    private final UserDAO userDAO;

    public OrganizationEmployee(Organization organization, EmployeeDAO employeeDAO, TenantRepository tenantRepository, UserDAO userDAO) {
        this.organization = organization;
        this.employeeDAO = employeeDAO;
        this.tenantRepository = tenantRepository;
        this.userDAO = userDAO;
    }

    @Override
    public List<User> subList(int from, int to) {
        return null;
    }

    @Override
    public Stream<User> list() {
        return employeeDAO.findAllByOrganizationId(organization.identity())
                .stream()
                .flatMap(employeeDO -> userDAO.findById(employeeDO.getId()).map(Stream::of).orElse(Stream.empty()))
                .map(userDO -> userDO.toUser(tenantRepository));
    }

    @Override
    public Optional<User> findByIdentity(Integer integer) {
        return employeeDAO.findById(integer)
                .flatMap(employeeDO -> userDAO.findById(integer))
                .map(userDO -> userDO.toUser(tenantRepository));
    }

    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void delete(User user) {
        employeeDAO.deleteById(user.identity());
    }
}
