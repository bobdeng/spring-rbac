package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.domain.UserDescription;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class UserDO {
    private Integer id;
    private String name;
    private Integer tenantId;


    public UserDO(User user, Tenant tenant) {
        this.id = user.identity();
        this.name = user.description().getName();
        this.tenantId = tenant.identity();
    }

    public User toUser() {
        return new User(id, new UserDescription(name));
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
