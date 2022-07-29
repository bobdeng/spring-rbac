package cn.bobdeng.rbac.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTokenTest {
    @Test
    public void encode_decode() {
        JwtConfig.prefix = "Token:";
        JwtConfig.expire = 86400;
        JwtConfig.token = "123456";
        LoginToken loginToken = new LoginToken(1, 2);
        String token = loginToken.toString();
        LoginToken decode = LoginToken.decode(token);
        assertEquals(decode,loginToken);
    }
}