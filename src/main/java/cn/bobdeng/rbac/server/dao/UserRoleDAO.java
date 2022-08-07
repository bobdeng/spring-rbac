package cn.bobdeng.rbac.server.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;


public interface UserRoleDAO extends CrudRepository<UserRoleDO, Integer> {
    Stream<UserRoleDO> findAllByUserId(Integer userId);
}
