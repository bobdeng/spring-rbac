package cn.bobdeng.rbac.server.dao;

import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<UserDO, Integer> {

}
