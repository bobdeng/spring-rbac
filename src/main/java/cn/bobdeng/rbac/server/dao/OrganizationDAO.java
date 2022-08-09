package cn.bobdeng.rbac.server.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrganizationDAO extends CrudRepository<OrganizationDO, Integer> {
    List<OrganizationDO> findAllByTenantId(Integer tenantId);
}
