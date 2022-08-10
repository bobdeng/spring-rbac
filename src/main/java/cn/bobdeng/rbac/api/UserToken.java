package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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

    public static UserToken decode(String token) {
        return JwtToken.decode(token, UserToken.class);
    }

    @Override
    public String toString() {
        return new JwtToken<>(this).toString();
    }
}
