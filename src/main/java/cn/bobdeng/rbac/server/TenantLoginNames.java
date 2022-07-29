package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.domain.LoginName;
import cn.bobdeng.rbac.domain.Tenant;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class TenantLoginNames implements Tenant.LoginNames {
    private Tenant tenant;
    private LoginNameDAO loginNameDAO;

    public TenantLoginNames(Tenant tenant, LoginNameDAO loginNameDAO) {
        this.tenant = tenant;
        this.loginNameDAO = loginNameDAO;
    }

    @Override
    public Optional<LoginName> findByLoginName(String name) {
        return loginNameDAO.findByLoginNameAndTenantId(name, tenant.identity())
                .map(LoginNameDO::toEntity);
    }

    @Override
    public List<LoginName> subList(int from, int to) {
        return null;
    }

    @Override
    public Stream<LoginName> list() {
        return null;
    }

    @Override
    public Optional<LoginName> findByIdentity(Integer integer) {
        return Optional.empty();
    }

    @Override
    public LoginName save(LoginName entity) {
        return loginNameDAO.save(new LoginNameDO(entity, tenant)).toEntity();
    }

    @Override
    public int size() {
        return 0;
    }
}
