package cn.bobdeng.rbac.server.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ParameterDAO extends CrudRepository<ParameterDO, Integer> {
    Optional<ParameterDO> findByKey(String key);
}
