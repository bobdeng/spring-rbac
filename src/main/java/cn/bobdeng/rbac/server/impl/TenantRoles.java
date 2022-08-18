package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.Role;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.server.dao.RoleDAO;
import cn.bobdeng.rbac.server.dao.RoleDO;
import cn.bobdeng.rbac.server.dao.UserRoleDAO;

import java.util.Optional;
import java.util.stream.Stream;

public class TenantRoles implements RbacContext.Roles {
    private final RoleDAO roleDAO;
    private final Tenant tenant;
    private final UserRoleDAO userRoleDAO;

    public TenantRoles(RoleDAO roleDAO, Tenant tenant, UserRoleDAO userRoleDAO) {
        this.roleDAO = roleDAO;
        this.tenant = tenant;
        this.userRoleDAO = userRoleDAO;
    }

    @Override
    public Stream<Role> list() {
        return roleDAO.findAllByTenantId(tenant.identity())
                .stream().map(RoleDO::toEntity);
    }

    @Override
    public Optional<Role> findByIdentity(Integer integer) {
        return roleDAO.findByTenantIdAndId(tenant.identity(), integer).map(RoleDO::toEntity);
    }

    @Override
    public Role save(Role entity) {
        return roleDAO.save(new RoleDO(entity, tenant)).toEntity();
    }

    @Override
    public void delete(Role role) {
        roleDAO.deleteById(role.getId());
        userRoleDAO.deleteAllByRoleId(role.identity());
    }
}
