package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.domain.User;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
public class LoginToken {
    private final Integer id;
    private final Integer tenant;

    public LoginToken(User user) {
        this(user.identity(), user.tenant().identity());
    }

    public LoginToken(Integer id, Integer tenant) {
        this.id = id;
        this.tenant = tenant;
    }

    public static LoginToken decode(String token) {
        return JwtToken.decode(token, LoginToken.class);
    }

    @Override
    public String toString() {
        return new JwtToken<>(this).toString();
    }
}
