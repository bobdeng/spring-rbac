package cn.bobdeng.rbac.server.dao;

import cn.bobdeng.rbac.domain.Domain;
import org.springframework.data.repository.CrudRepository;

public interface DomainDAO extends CrudRepository<Domain, Integer> {
}
