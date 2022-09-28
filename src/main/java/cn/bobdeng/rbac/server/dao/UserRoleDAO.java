package cn.bobdeng.rbac.server.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRoleDAO extends CrudRepository<UserRoleDO, Integer> {
    List<UserRoleDO> findAllByUserId(Integer userId);

    void deleteAllByRoleId(Integer roleId);

    void deleteAllByUserId(Integer identity);
}
