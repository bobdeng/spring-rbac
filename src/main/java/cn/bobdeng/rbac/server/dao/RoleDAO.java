package cn.bobdeng.rbac.server.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleDAO extends CrudRepository<RoleDO, Integer> {
    List<RoleDO> findAllByTenantId(int tenantId);
}
