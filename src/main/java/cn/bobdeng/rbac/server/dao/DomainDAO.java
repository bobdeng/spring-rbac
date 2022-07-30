package cn.bobdeng.rbac.server.dao;

import cn.bobdeng.rbac.domain.Domain;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DomainDAO extends CrudRepository<Domain, Integer> {
    Optional<Domain> findByDescriptionDomain(String domain);
}
