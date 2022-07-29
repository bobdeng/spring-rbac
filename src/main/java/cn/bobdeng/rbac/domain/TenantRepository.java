package cn.bobdeng.rbac.domain;

import java.util.Optional;
import java.util.stream.Stream;

public interface TenantRepository extends EntityList<Integer, Tenant> {
    Tenant save(Tenant tenant);

    Stream<Tenant> findByName(String name, int from, int to);

    Optional<Tenant> findById(Integer id);
}
