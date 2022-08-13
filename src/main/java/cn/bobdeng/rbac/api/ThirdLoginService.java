package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.Cookies;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.domain.UserDescription;
import cn.bobdeng.rbac.domain.third.ThirdDescription;
import cn.bobdeng.rbac.domain.third.ThirdIdentity;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class ThirdLoginService {
    public UserToken login(Tenant tenant, ThirdLoginForm thirdLoginForm, HttpServletResponse response) {
        UserToken userToken = getUserToken(tenant, thirdLoginForm);
        Cookie cookie = new Cookie(Cookies.AUTHORIZATION, userToken.toString());
        cookie.setPath("/");
        response.addCookie(cookie);
        return userToken;
    }

    private UserToken getUserToken(Tenant tenant, ThirdLoginForm thirdLoginForm) {
        Optional<ThirdIdentity> thirdIdentity = tenant.thirdIdentities().findByNameAndIdentity(thirdLoginForm.getThirdName(), thirdLoginForm.getIdentity());
        if (thirdIdentity.isEmpty()) {
            User user = newUser(tenant, thirdLoginForm);
            return new UserToken(user, tenant);
        }
        Integer userId = thirdIdentity.get().description().getUserId();
        User user = tenant.users().findByIdentity(userId).orElseThrow(ObjectNotFoundException::new);
        return new UserToken(user, tenant);
    }

    private User newUser(Tenant tenant, ThirdLoginForm thirdLoginForm) {
        User user = tenant.addUser(new UserDescription(thirdLoginForm.getUserName()));
        ThirdDescription thirdDescription = new ThirdDescription(thirdLoginForm.getIdentity(), thirdLoginForm.getThirdName(), user.identity());
        tenant.newThirdIdentity(thirdDescription);
        return user;
    }
}
