package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.Tenant;
import org.springframework.data.repository.CrudRepository;

public interface TenantDAO extends CrudRepository<Tenant, Integer> {
}
