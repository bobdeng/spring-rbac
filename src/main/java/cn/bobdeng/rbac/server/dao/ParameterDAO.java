package cn.bobdeng.rbac.server.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ParameterDAO extends CrudRepository<ParameterDO, Integer> {
    List<ParameterDO> findAllByTenantId(Integer tenantId);

    Optional<ParameterDO> findByKeyAndTenantId(String key, Integer tenantId);
}
