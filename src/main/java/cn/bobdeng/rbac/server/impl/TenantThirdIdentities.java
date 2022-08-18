package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.domain.rbac.ThirdIdentity;
import cn.bobdeng.rbac.server.dao.ThirdIdentityDAO;
import cn.bobdeng.rbac.server.dao.ThirdIdentityDO;

import java.util.Optional;

public class TenantThirdIdentities implements RbacContext.ThirdIdentities {
    private Tenant tenant;
    private ThirdIdentityDAO thirdIdentityDAO;

    public TenantThirdIdentities(Tenant tenant, ThirdIdentityDAO thirdIdentityDAO) {
        this.tenant = tenant;
        this.thirdIdentityDAO = thirdIdentityDAO;
    }

    @Override
    public ThirdIdentity save(ThirdIdentity entity) {
        return thirdIdentityDAO.save(ThirdIdentityDO.builder()
                .tenantId(tenant.identity())
                .thirdName(entity.description().getName())
                .userId(entity.description().getUserId())
                .identity(entity.description().getIdentity())
                .build()).toEntity();
    }

    @Override
    public Optional<ThirdIdentity> findByNameAndIdentity(String thirdName, String identity) {
        return thirdIdentityDAO.findByThirdNameAndIdentity(thirdName, identity)
                .map(ThirdIdentityDO::toEntity);
    }
}
