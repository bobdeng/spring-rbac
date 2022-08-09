package cn.bobdeng.rbac.server.dao;

import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.server.impl.UserRolesImpl;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class UserDO {
    private Integer id;
    private String name;
    private Integer tenantId;
    private String status;


    public UserDO(User user, Tenant tenant) {
        this.id = user.identity();
        this.name = user.description().getName();
        this.tenantId = tenant.identity();
        this.status = user.description().getStatus().getStatus();
    }

    public User toUser(TenantRepository tenantRepository) {
        User user = new User(id, new UserDescription(name, User.UserStatus.of(status)));
        user.setUserPassword(tenantRepository.userPassword(user));
        user.setTenant(() -> tenantRepository.findByIdentity(tenantId).orElse(null));
        user.setUserRoles(tenantRepository.userRoles(user));
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDO userDO = (UserDO) o;
        return Objects.equals(id, userDO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
