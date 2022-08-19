package cn.bobdeng.rbac.api.oauth;

import cn.bobdeng.rbac.ClearTable;
import cn.bobdeng.rbac.Cookies;
import cn.bobdeng.rbac.api.E2ETest;
import cn.bobdeng.rbac.api.JwtToken;
import cn.bobdeng.rbac.api.UserToken;
import cn.bobdeng.rbac.api.UserWithTenantFixture;
import cn.bobdeng.rbac.domain.rbac.User;
import cn.bobdeng.rbac.domain.rbac.ThirdDescription;
import cn.bobdeng.rbac.utils.HttpClient;
import cn.bobdeng.rbac.utils.HttpRequest;
import cn.bobdeng.rbac.utils.HttpResponse;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WxLoginCallbackTest extends E2ETest {
    @Autowired
    UserWithTenantFixture userWithTenantFixture;
    @Autowired
    ClearTable clearTable;
    @Autowired
    MockHttpClient mockHttpClient;
    @Autowired
    WxConfig wxConfig;
    private String openId = "123456";
    private String code = "888999";
    private String state = "state000999";
    private String accessToken = "xxxbbbssss";

    @BeforeEach
    public void setup() throws IOException {
        clearLogin();
        clearTable.clearTable("t_rbac_third_identity");
        userWithTenantFixture.init();
        mockHttpClient.setHttpClient(mock(HttpClient.class));

        when(mockHttpClient.getHttpClient().execute(getAccessTokenRequest())).thenReturn(accessTokenResponse());
        when(mockHttpClient.getHttpClient().execute(getGetUserInfoRequest())).thenReturn(userInfoResponse());
    }

    @Test
    public void should_login_when_already_bind_to_wx() {
        Integer userId = userWithTenantFixture.user().identity();
        ThirdDescription thirdDescription = new ThirdDescription(openId, "微信", userId);
        userWithTenantFixture.getRbac().newThirdIdentity(thirdDescription);
        WxCallbackPage wxCallbackPage = new WxCallbackPage(webDriverHandler);
        wxCallbackPage.open(code, state);
        waitUntil(() -> wxCallbackPage.hasText("登录成功"), 100);
        waitUntil(() -> wxCallbackPage.hasText("张三"), 100);
        assertLoginWithUser(userId);
    }

    private void assertLoginWithUser(Integer userId) {
        Cookie cookie = getCookie(Cookies.AUTHORIZATION);
        UserToken userToken = JwtToken.decode(cookie.getValue(), UserToken.class);
        assertEquals(userId, userToken.getId());
    }

    private HttpResponse accessTokenResponse() {
        WxAccessTokenResult wxAccessTokenResult = new WxAccessTokenResult(accessToken, openId);
        return new HttpResponse(200, new Gson().toJson(wxAccessTokenResult));
    }

    private HttpRequest getAccessTokenRequest() {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wxConfig.getAppId() + "&secret=" + wxConfig.getAppSecret() + "&code=" + code + "&grant_type=authorization_code";
        return new HttpRequest(url);
    }

    @Test
    public void should_create_when_not_exist() {
        WxCallbackPage wxCallbackPage = new WxCallbackPage(webDriverHandler);
        wxCallbackPage.open(code, state);
        waitUntil(() -> wxCallbackPage.hasText("登录成功"), 100);
        waitUntil(() -> wxCallbackPage.hasText("李四"), 100);
        User lisi = userWithTenantFixture.getRbac().users().findByName("李四").get(0);
        assertLoginWithUser(lisi.identity());
    }

    @Test
    public void should_bind_to_user_when_login() {
        userLogin(userWithTenantFixture.user());
        WxCallbackPage wxCallbackPage = new WxCallbackPage(webDriverHandler);
        wxCallbackPage.open(code, state);
        waitUntil(() -> wxCallbackPage.hasText("登录成功"), 100);
        waitUntil(() -> wxCallbackPage.hasText("张三"), 100);
        assertLoginWithUser(userWithTenantFixture.user().identity());
    }

    @Test
    public void should_throw_when_get_access_token_fail() throws IOException {
        when(mockHttpClient.getHttpClient().execute(getAccessTokenRequest())).thenReturn(failedAccessTokenResponse());
        userLogin(userWithTenantFixture.user());
        WxCallbackPage wxCallbackPage = new WxCallbackPage(webDriverHandler);
        wxCallbackPage.open(code, state);
        waitUntil(() -> wxCallbackPage.hasText("登录失败"), 100);
    }

    @Test
    public void should_throw_when_get_user_token_fail() throws IOException {
        when(mockHttpClient.getHttpClient().execute(getGetUserInfoRequest())).thenReturn(failedAccessTokenResponse());
        userLogin(userWithTenantFixture.user());
        WxCallbackPage wxCallbackPage = new WxCallbackPage(webDriverHandler);
        wxCallbackPage.open(code, state);
        waitUntil(() -> wxCallbackPage.hasText("登录失败"), 100);
    }

    @Test
    public void should_fail_when_state_fail() {
        userLogin(userWithTenantFixture.user());
        WxCallbackPage wxCallbackPage = new WxCallbackPage(webDriverHandler);
        wxCallbackPage.open(code, "123456");
        waitUntil(() -> wxCallbackPage.hasText("登录失败"), 100);
    }

    private HttpResponse failedAccessTokenResponse() {
        return new HttpResponse(200, "{\"errcode\":40029,\"errmsg\":\"invalid code\"}");
    }

    private HttpResponse userInfoResponse() {
        WxUserInfo wxUserInfo = new WxUserInfo("李四");
        String userInfo = new Gson().toJson(wxUserInfo);
        HttpResponse response = new HttpResponse(200, userInfo);
        return response;
    }

    private HttpRequest getGetUserInfoRequest() {
        return new HttpRequest("https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId);
    }
}
