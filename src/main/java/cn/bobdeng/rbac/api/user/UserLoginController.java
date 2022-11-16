package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.Cookies;
import cn.bobdeng.rbac.api.LoginForm;
import cn.bobdeng.rbac.api.UserToken;
import cn.bobdeng.rbac.domain.rbac.LoginName;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.domain.rbac.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/1.0/user_sessions")
public class UserLoginController extends RbacController {
    public UserLoginController(TenantRepository tenantRepository, RbacContext rbacContext) {
        super(tenantRepository, rbacContext);
    }

    @PostMapping
    public void login(@RequestBody LoginForm loginForm, HttpServletResponse response,
                      @RequestAttribute("tenant") Tenant tenant) {
        User user = getRbac(tenant).loginNames().findByLoginName(loginForm.getLoginName())
                .map(LoginName::user)
                .filter(it -> it.verifyPassword(loginForm.getPassword()))
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));
        Cookie cookie = new Cookie(Cookies.AUTHORIZATION, new UserToken(user).toTokenString());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @DeleteMapping
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie(Cookies.AUTHORIZATION, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
