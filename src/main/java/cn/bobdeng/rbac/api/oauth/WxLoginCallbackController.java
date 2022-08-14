package cn.bobdeng.rbac.api.oauth;

import cn.bobdeng.rbac.api.UserToken;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.domain.UserDescription;
import cn.bobdeng.rbac.domain.third.ThirdDescription;
import cn.bobdeng.rbac.domain.third.ThirdIdentity;
import cn.bobdeng.rbac.security.Session;
import cn.bobdeng.rbac.utils.HttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public record WxLoginCallbackController(HttpClient httpClient,
                                        WxConfig wxConfig) {

    @GetMapping("/wx_callback")
    public String wxCallback(Model model,
                             HttpServletResponse response,
                             @RequestParam("code") String code,
                             @RequestAttribute("tenant") Tenant tenant,
                             @RequestAttribute(value = "session", required = false) Session session) {
        WxLoginResult wxLoginResult = new WxLoginResult(httpClient, code, wxConfig);
        wxLoginResult.read();
        if (!wxLoginResult.isSuccess()) {
            return "oauth_fail";
        }
        User user = tenant.thirdIdentities().findByNameAndIdentity(OAuthNames.WX, wxLoginResult.openId())
                .map(thirdIdentity -> getUserByThirdIdentity(tenant, thirdIdentity))
                .orElseGet(() -> bindOrCreateUser(tenant, session, wxLoginResult));
        model.addAttribute("name", user.getDescription().getName());
        new UserToken(user).writeToResponse(response);
        return "oauth_success";
    }

    private User getUserByThirdIdentity(Tenant tenant, ThirdIdentity thirdIdentity) {
        Integer userId = thirdIdentity.description().getUserId();
        return tenant.users().findByIdentity(userId).orElseThrow();
    }

    private User bindOrCreateUser(Tenant tenant, Session session, WxLoginResult wxLoginResult) {
        User user = Optional.ofNullable(session)
                .map(Session::userId)
                .flatMap(userId -> tenant.users().findByIdentity(userId))
                .orElseGet(() -> tenant.addUser(new UserDescription(wxLoginResult.nickname())));
        tenant.newThirdIdentity(new ThirdDescription(wxLoginResult.openId(), OAuthNames.WX, user.identity()));
        return user;
    }

}
