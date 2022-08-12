package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.archtype.EntityList;

import java.util.Optional;

public interface DomainRepository extends EntityList<Integer,Domain> {
    Optional<Domain> findByDomain(String domain);

    void delete(Integer id);
}
