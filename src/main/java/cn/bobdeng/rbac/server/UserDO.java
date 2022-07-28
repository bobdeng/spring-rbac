package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.Tenant;
import cn.bobdeng.rbac.User;
import cn.bobdeng.rbac.UserDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
}
