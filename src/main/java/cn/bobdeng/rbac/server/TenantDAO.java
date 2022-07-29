package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.domain.Tenant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface TenantDAO extends CrudRepository<Tenant, Integer> {
    @Query("""
            select t from cn.bobdeng.rbac.domain.Tenant t 
            where t.description.name  like :name%
            """)
    Stream<Tenant> findByName(String name);
    Stream<Tenant> findByDescriptionName(String name);
}
