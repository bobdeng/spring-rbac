package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.domain.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class LoginController {
    private TenantRepository tenantRepository;

    public LoginController(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @PostMapping("/rbac/sessions")
    public String login(@RequestBody @Valid LoginForm form, HttpServletResponse response) {
        Optional<User> optionalUser = checkUserLogin(form);
        optionalUser.ifPresent(user -> setLoginResponse(response, user));
        if (!optionalUser.isPresent()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "登录失败";
        }
        return "登录成功";
    }

    @NotNull
    private Optional<User> checkUserLogin(LoginForm form) {
        return tenantRepository.findByName(form.getTenant())
                .flatMap(tenant -> tenant.getLoginNames().findByLoginName(form.getLoginName()))
                .map(LoginName::user)
                .filter(user -> user.verifyPassword(form.getPassword()));
    }

    private void setLoginResponse(HttpServletResponse response, User user) {
        Tenant tenant = user.tenant();
        String token = new LoginToken(user).toString();
        Cookie authorization = new Cookie("Authorization", token);
        response.addCookie(authorization);
    }
}
