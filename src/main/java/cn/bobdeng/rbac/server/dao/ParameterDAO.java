package cn.bobdeng.rbac.server.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParameterDAO extends CrudRepository<ParameterDO, Integer> {
    List<ParameterDO> findAllByTenantId(Integer tenantId);
}
