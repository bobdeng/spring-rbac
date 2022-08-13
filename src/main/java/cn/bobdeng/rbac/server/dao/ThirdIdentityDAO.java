package cn.bobdeng.rbac.server.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ThirdIdentityDAO extends CrudRepository<ThirdIdentityDO, Integer> {
    List<ThirdIdentityDO> findAllByTenantId(Integer tenantId);

    Optional<ThirdIdentityDO> findByThirdNameAndIdentity(String name, String identity);
}
