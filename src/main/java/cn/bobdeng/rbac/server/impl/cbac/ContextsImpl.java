package cn.bobdeng.rbac.server.impl.cbac;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.cbac.CbacContext;
import cn.bobdeng.rbac.domain.cbac.Context;
import cn.bobdeng.rbac.domain.cbac.ContextObject;
import cn.bobdeng.rbac.server.dao.CbacContextDAO;
import cn.bobdeng.rbac.server.dao.CbacContextDO;

import java.util.stream.Stream;

public class ContextsImpl implements CbacContext.Contexts {
    private final Tenant tenant;
    private final CbacContextDAO contextDAO;

    public ContextsImpl(Tenant tenant, CbacContextDAO contextDAO) {
        this.tenant = tenant;
        this.contextDAO = contextDAO;
    }

    @Override
    public Stream<Context> findByObject(ContextObject object) {
        return contextDAO.findByTenantIdAndObjectTypeAndObjectId(tenant.getId(), object.getType(), object.getIdentity())
                .stream().map(CbacContextDO::toEntity);
    }

    @Override
    public Context save(Context entity) {
        return contextDAO.save(new CbacContextDO(entity, tenant)).toEntity();
    }
}
