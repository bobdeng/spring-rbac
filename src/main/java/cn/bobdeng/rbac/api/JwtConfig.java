package cn.bobdeng.rbac.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    public static String token;
    @Value("${jwt.expire}")
    public static long expire;
    @Value("${jwt.prefix}")
    public static String prefix;

    @Value("${jwt.token}")
    public void setToken(String name) {
        token = name;
    }

    @Value("${jwt.expire}")
    public void setExpire(long expire) {
        JwtConfig.expire = expire;
    }

    @Value("${jwt.prefix}")
    public void setPrefix(String name) {
        prefix = name;
    }
}
