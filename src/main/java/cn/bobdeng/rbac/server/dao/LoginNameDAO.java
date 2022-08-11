package cn.bobdeng.rbac.server.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LoginNameDAO extends CrudRepository<LoginNameDO, Integer> {
    Optional<LoginNameDO> findByLoginNameAndTenantId(String loginName, Integer tenantId);

    Optional<LoginNameDO> findByUserId(Integer userId);
    Optional<LoginNameDO> findByIdAndTenantId(Integer id,Integer tenantId);
}
