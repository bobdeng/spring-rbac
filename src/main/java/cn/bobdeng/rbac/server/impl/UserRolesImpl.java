package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.Role;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.server.dao.RoleDAO;
import cn.bobdeng.rbac.server.dao.RoleDO;
import cn.bobdeng.rbac.server.dao.UserRoleDAO;
import cn.bobdeng.rbac.server.dao.UserRoleDO;

import java.util.List;
import java.util.Optional;
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
    public List<Role> subList(int from, int to) {
        return null;
    }

    @Override
    public Stream<Role> list() {
        return userRoleDAO.findAllByUserId(user.getId())
                .flatMap(userRoleDO -> roleDAO.findById(userRoleDO.getRoleId()).map(Stream::of).orElse(Stream.empty()))
                .map(RoleDO::toEntity);
    }

    @Override
    public Optional<Role> findByIdentity(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Role save(Role entity) {
        userRoleDAO.save(UserRoleDO.builder()
                .userId(user.getId())
                .roleId(entity.getId())
                .build());
        return entity;
    }

    @Override
    public int size() {
        return 0;
    }
}
