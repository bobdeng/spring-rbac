package cn.bobdeng.rbac.server.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDAO extends CrudRepository<UserDO, Integer> {
    List<UserDO> findAllByTenantIdAndNameLike(Integer tenantId, String name);
}
