package cn.bobdeng.rbac.server.impl.rbac;

import cn.bobdeng.rbac.domain.rbac.Role;
import cn.bobdeng.rbac.domain.rbac.User;
import cn.bobdeng.rbac.server.dao.RoleDAO;
import cn.bobdeng.rbac.server.dao.RoleDO;
import cn.bobdeng.rbac.server.dao.UserRoleDAO;
import cn.bobdeng.rbac.server.dao.UserRoleDO;

import java.util.stream.Stream;

public class UserRolesImpl implements User.UserRoles {
    private User user;
    private UserRoleDAO userRoleDAO;
    private RoleDAO roleDAO;

    public UserRolesImpl(User user, UserRoleDAO userRoleDAO, RoleDAO roleDAO) {
        this.user = user;
        this.userRoleDAO = userRoleDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public Stream<Role> list() {
        return userRoleDAO.findAllByUserId(user.getId())
                .stream()
                .flatMap(userRoleDO -> roleDAO.findById(userRoleDO.getRoleId()).map(Stream::of).orElse(Stream.empty()))
                .map(RoleDO::toEntity);
    }

    @Override
    public Role save(Role entity) {
        userRoleDAO.save(UserRoleDO.builder()
                .userId(user.getId())
                .roleId(entity.getId())
                .build());
        return entity;
    }
}
