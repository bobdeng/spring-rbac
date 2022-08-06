package cn.bobdeng.rbac.server.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoleDAO extends CrudRepository<RoleDO, Integer> {
    List<RoleDO> findAllByTenantId(int tenantId);

    Optional<RoleDO> findByTenantIdAndId(int tenantId, int id);
}
