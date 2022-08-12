package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.LoginName;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.server.dao.LoginNameDAO;
import cn.bobdeng.rbac.server.dao.LoginNameDO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class TenantLoginNames implements Tenant.LoginNames {
    private Tenant tenant;
    private LoginNameDAO loginNameDAO;
    private TenantRepository tenantRepository;

    public TenantLoginNames(Tenant tenant, LoginNameDAO loginNameDAO, TenantRepository tenantRepository) {
        this.tenant = tenant;
        this.loginNameDAO = loginNameDAO;
        this.tenantRepository = tenantRepository;
    }

    @Override
    public Optional<LoginName> findByLoginName(String name) {
        return loginNameDAO.findByLoginNameAndTenantId(name, tenant.identity())
                .map((loginNameDO) -> loginNameDO.toEntity(tenantRepository.users(tenant)));
    }

    @Override
    public void delete(Integer id) {
        loginNameDAO.findByIdAndTenantId(id, tenant.identity())
                .ifPresent(loginNameDAO::delete);
    }

    @Override
    public Optional<LoginName> findByUser(Integer id) {
        return loginNameDAO.findByUserId(id).map(loginNameDO -> loginNameDO.toEntity(tenantRepository.users(tenant)));
    }

    @Override
    public LoginName save(LoginName entity) {
        return loginNameDAO.save(new LoginNameDO(entity, tenant)).toEntity(tenantRepository.users(tenant));
    }

}
