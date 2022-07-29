package cn.bobdeng.rbac.domain;

import java.util.Optional;
import java.util.stream.Stream;

public interface TenantRepository extends EntityList<Integer, Tenant> {
    Tenant save(Tenant tenant);

    Stream<Tenant> findByName(String name, int from, int to);

    Optional<Tenant> findById(Integer id);

    Optional<Tenant> findByName(String tenantName);

    Tenant.Users users(Tenant tenant);

    Tenant.LoginNames loginNames(Tenant tenant);

    User.UserPassword userPassword(User user);
}
