package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.Tenants;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class LoginController {
    private TenantRepository tenantRepository;

    public LoginController(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @PostMapping("/rbac/sessions")
    public void login(@RequestBody @Valid LoginForm form, HttpServletResponse response) {

        tenantRepository.findByName(form.getTenant())
                .flatMap(tenant -> tenant.getLoginNames().findByLoginName(form.getLoginName()))
                .filter(loginName -> loginName.description().getUser().verifyPassword(form.getPassword()))
                .ifPresent(loginName -> {
                    String token = "123";
                    Cookie authorization = new Cookie("Authorization", token);
                    response.addCookie(authorization);
                });
    }
}
