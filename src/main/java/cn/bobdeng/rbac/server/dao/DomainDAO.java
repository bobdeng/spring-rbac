package cn.bobdeng.rbac.server.dao;

import cn.bobdeng.rbac.domain.Domain;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface DomainDAO extends CrudRepository<Domain, Integer> {
    Optional<Domain> findByDescriptionDomain(String domain);

    Stream<Domain> findAllByDescriptionTenantId(Integer tenantId);
}
