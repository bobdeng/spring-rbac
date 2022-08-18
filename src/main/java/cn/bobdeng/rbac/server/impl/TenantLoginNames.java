package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.LoginName;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.server.dao.LoginNameDAO;
import cn.bobdeng.rbac.server.dao.LoginNameDO;
import cn.bobdeng.rbac.server.impl.rbac.RbacContextImpl;

import java.util.Optional;

public class TenantLoginNames implements RbacContext.LoginNames {
    private Tenant tenant;
    private LoginNameDAO loginNameDAO;
    private RbacContext rbacContext;

    public TenantLoginNames(Tenant tenant, LoginNameDAO loginNameDAO, RbacContext rbacContext) {
        this.tenant = tenant;
        this.loginNameDAO = loginNameDAO;
        this.rbacContext = rbacContext;
    }

    @Override
    public Optional<LoginName> findByLoginName(String name) {
        return loginNameDAO.findByLoginNameAndTenantId(name, tenant.identity())
                .map((loginNameDO) -> loginNameDO.toEntity(rbacContext.users(tenant)));
    }

    @Override
    public void delete(Integer id) {
        loginNameDAO.findByIdAndTenantId(id, tenant.identity())
                .ifPresent(loginNameDAO::delete);
    }

    @Override
    public Optional<LoginName> findByUser(Integer id) {
        return loginNameDAO.findByUserId(id).map(loginNameDO -> loginNameDO.toEntity(rbacContext.users(tenant)));
    }

    @Override
    public LoginName save(LoginName entity) {
        return loginNameDAO.save(new LoginNameDO(entity, tenant)).toEntity(rbacContext.users(tenant));
    }

}
