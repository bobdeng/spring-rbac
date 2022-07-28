package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.Tenant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface TenantDAO extends CrudRepository<Tenant, Integer> {
    @Query("""
            select t from cn.bobdeng.rbac.Tenant t 
            where t.tenantDescription.name  like :name%
            """)
    Stream<Tenant> findByName(String name);
}
