package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class UserToken {
    private final Integer id;
    private final Integer tenant;

    public UserToken(User user) {
        this(user.identity(), user.tenant().identity());
    }

    public UserToken(Integer id, Integer tenant) {
        this.id = id;
        this.tenant = tenant;
    }

    public UserToken(User user, Tenant tenant) {
        this(user.identity(), tenant.identity());
    }

    public static UserToken decode(String token) {
        return JwtToken.decode(token, UserToken.class);
    }

    @Override
    public String toString() {
        return new JwtToken<>(this).toString();
    }
}
