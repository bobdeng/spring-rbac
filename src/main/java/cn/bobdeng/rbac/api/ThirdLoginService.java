package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.Cookies;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.domain.UserDescription;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.domain.third.ThirdDescription;
import cn.bobdeng.rbac.domain.third.ThirdIdentity;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class ThirdLoginService {
    private final TenantRepository tenantRepository;

    public ThirdLoginService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public UserToken login(Tenant tenant, ThirdLoginForm thirdLoginForm, HttpServletResponse response) {
        UserToken userToken = getUserToken(tenant, thirdLoginForm);
        Cookie cookie = new Cookie(Cookies.AUTHORIZATION, userToken.toTokenString());
        cookie.setPath("/");
        response.addCookie(cookie);
        return userToken;
    }

    private UserToken getUserToken(Tenant tenant, ThirdLoginForm thirdLoginForm) {
        Optional<ThirdIdentity> thirdIdentity = getRbac(tenant).thirdIdentities().findByNameAndIdentity(thirdLoginForm.getThirdName(), thirdLoginForm.getIdentity());
        if (thirdIdentity.isEmpty()) {
            User user = newUser(tenant, thirdLoginForm);
            return new UserToken(user, tenant);
        }
        Integer userId = thirdIdentity.get().description().getUserId();
        User user = getRbac(tenant).users().findByIdentity(userId).orElseThrow(ObjectNotFoundException::new);
        return new UserToken(user, tenant);
    }

    private User newUser(Tenant tenant, ThirdLoginForm thirdLoginForm) {
        RbacContext.Rbac rbac = getRbac(tenant);
        User user = rbac.addUser(new UserDescription(thirdLoginForm.getUserName()));
        ThirdDescription thirdDescription = new ThirdDescription(thirdLoginForm.getIdentity(), thirdLoginForm.getThirdName(), user.identity());
        rbac.newThirdIdentity(thirdDescription);
        return user;
    }

    private RbacContext.Rbac getRbac(Tenant tenant) {
        return tenantRepository.rbacContext().asRbac(tenant);
    }
}
