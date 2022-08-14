package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.Cookies;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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

    public String toTokenString() {
        return new JwtToken<>(this).toString();
    }

    public void writeToResponse(HttpServletResponse response) {
        Cookie cookie = new Cookie(Cookies.AUTHORIZATION, new JwtToken<>(this).toString());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
