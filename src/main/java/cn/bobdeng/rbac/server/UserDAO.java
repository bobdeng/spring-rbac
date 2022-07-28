package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<UserDO, Integer> {

}
