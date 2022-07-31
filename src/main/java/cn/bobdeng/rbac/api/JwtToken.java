package cn.bobdeng.rbac.api;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtToken<T> {
    T object;

    public JwtToken(T object) {
        this.object = object;
    }

    public static <T> T decode(String token, Class<T> clz) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(JwtConfig.token)
                    .parseClaimsJws(token.replace(JwtConfig.prefix, ""));
            return new Gson().fromJson(jws.getBody().getSubject(), clz);
        } catch (Exception e) {
            log.info(token);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return JwtConfig.prefix + Jwts.builder()
                .setSubject(new Gson().toJson(object))
                .setExpiration(new Date(System.currentTimeMillis() + JwtConfig.expire))
                .signWith(SignatureAlgorithm.HS256, JwtConfig.token)
                .compact();
    }
}
