package cn.bobdeng.rbac.domain;

import java.util.Optional;

public interface DomainRepository extends EntityList<Integer,Domain>{
    Optional<Domain> findByDomain(String domain);
}
