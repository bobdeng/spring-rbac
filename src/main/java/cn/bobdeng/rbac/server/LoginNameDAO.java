package cn.bobdeng.rbac.server;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LoginNameDAO extends CrudRepository<LoginNameDO, Integer> {
   Optional<LoginNameDO> findByLoginNameAndTenantId(String loginName, Integer tenantId);
}
