package cn.bobdeng.rbac.domain;

import java.util.Optional;

public interface TenantRepository extends EntityList<Integer, Tenant> {
    Tenant save(Tenant tenant);

    Page<Tenant> findByName(String name, int page, int size);

    Optional<Tenant> findById(Integer id);

    Optional<Tenant> findByName(String tenantName);

    Tenant.Users users(Tenant tenant);

    Tenant.LoginNames loginNames(Tenant tenant);

    User.UserPassword userPassword(User user);

    User.UserRoles userRoles(User user);
}
