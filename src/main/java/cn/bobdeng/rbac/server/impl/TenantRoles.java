package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.Role;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.server.dao.RoleDAO;
import cn.bobdeng.rbac.server.dao.RoleDO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class TenantRoles implements Tenant.Roles {
    private final RoleDAO roleDAO;
    private final Tenant tenant;
    public TenantRoles(RoleDAO roleDAO, Tenant tenant) {
        this.roleDAO = roleDAO;
        this.tenant = tenant;
    }

    @Override
    public List<Role> subList(int from, int to) {
        return null;
    }

    @Override
    public Stream<Role> list() {
        return null;
    }

    @Override
    public Optional<Role> findByIdentity(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Role save(Role entity) {
        return roleDAO.save(new RoleDO(entity,tenant)).toEntity();
    }

    @Override
    public int size() {
        return 0;
    }
}
