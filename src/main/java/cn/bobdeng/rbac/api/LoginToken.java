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
        try {
            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(JwtConfig.token)
                    .parseClaimsJws(token.replace(JwtConfig.prefix, ""));
            return new Gson().fromJson(jws.getBody().getSubject(), LoginToken.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return JwtConfig.prefix + Jwts.builder()
                .setSubject(new Gson().toJson(this))
                .setExpiration(new Date(System.currentTimeMillis() + JwtConfig.expire))
                .signWith(SignatureAlgorithm.HS256, JwtConfig.token)
                .compact();
    }
}
