package cn.bobdeng.rbac.server.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeDAO extends CrudRepository<EmployeeDO, Integer> {
    List<EmployeeDO> findAllByOrganizationId(Integer organizationId);
}
