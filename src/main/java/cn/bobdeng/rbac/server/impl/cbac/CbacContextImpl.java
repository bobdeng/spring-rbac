package cn.bobdeng.rbac.server.impl.cbac;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.cbac.CbacContext;
import cn.bobdeng.rbac.domain.cbac.CbacImpl;
import cn.bobdeng.rbac.server.dao.CbacContextDAO;
import org.springframework.stereotype.Service;

@Service
public class CbacContextImpl implements CbacContext {

    private CbacContextDAO contextDAO;

    public CbacContextImpl(CbacContextDAO contextDAO) {
        this.contextDAO = contextDAO;
    }

    @Override
    public Cbac asCbac(Tenant tenant) {
        return new CbacImpl(new ContextsImpl(tenant, contextDAO));
    }
}
