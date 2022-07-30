package cn.bobdeng.rbac.server.dao;

import cn.bobdeng.rbac.domain.Password;
import org.springframework.data.repository.CrudRepository;

public interface PasswordDAO extends CrudRepository<Password, Integer> {
}
