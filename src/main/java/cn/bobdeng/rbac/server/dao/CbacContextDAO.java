package cn.bobdeng.rbac.server.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CbacContextDAO extends CrudRepository<CbacContextDO, Integer> {
    List<CbacContextDO> findByTenantIdAndObjectTypeAndObjectId(Integer tenantId,String objectType,String objectId);
}
