package cn.bobdeng.rbac.server;

import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<UserDO, Integer> {

}
