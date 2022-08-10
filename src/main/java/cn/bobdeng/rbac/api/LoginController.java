package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.Cookies;
import cn.bobdeng.rbac.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.net.MalformedURLException;
import java.util.Optional;

@Controller
public class LoginController {
    private TenantRepository tenantRepository;
    private DomainRepository domainRepository;

    public LoginController(TenantRepository tenantRepository, DomainRepository domainRepository) {
        this.tenantRepository = tenantRepository;
        this.domainRepository = domainRepository;
    }

    @GetMapping("/rbac/login")
    public String loginPage(HttpServletRequest request, Model model) throws MalformedURLException {
        Tenant tenant = tenant(request);
        model.addAttribute("loginForm", new LoginForm(tenant, request.getParameter("from")));
        return "login";
    }

    @ModelAttribute("tenant")
    public Tenant tenant(HttpServletRequest request) throws MalformedURLException {
        String host = RequestUtils.getHost(request);
        return domainRepository.findByDomain(host)
                .map(Domain::tenant).orElse(null);
    }

    @PostMapping("/rbac/sessions")
    @Transactional
    public String login(Model model,
                        @ModelAttribute("loginForm") LoginForm loginForm,
                        HttpServletResponse response) {
        Optional<User> user = checkUserLogin(loginForm);
        if (user.isPresent()) {
            setLoginResponse(response, user.get());
            return "login_success";
        }
        model.addAttribute("error", "登录失败");
        return "login";
    }

    private Optional<User> checkUserLogin(LoginForm form) {
        return tenantRepository.findByName(form.getTenant())
                .flatMap(tenant -> tenant.loginNames().findByLoginName(form.getLoginName()))
                .map(LoginName::user)
                .filter(user -> user.verifyPassword(form.getPassword()));
    }

    private void setLoginResponse(HttpServletResponse response, User user) {
        String token = new UserToken(user).toString();
        Cookie authorization = new Cookie(Cookies.AUTHORIZATION, token);
        response.addCookie(authorization);
    }
}
