package cn.bobdeng.rbac.api.oauth;

import cn.bobdeng.rbac.utils.HttpClient;
import cn.bobdeng.rbac.utils.HttpRequest;
import cn.bobdeng.rbac.utils.HttpResponse;
import com.google.gson.Gson;
import lombok.Getter;

@Getter
public class WxLoginResult {
    private WxAccessTokenResult accessTokenResult;
    private WxUserInfo userInfo;
    private final String code;
    private final WxConfig wxConfig;
    private final HttpClient httpClient;

    public WxLoginResult(HttpClient httpClient, String code, WxConfig wxConfig) {
        this.httpClient = httpClient;
        this.code = code;
        this.wxConfig = wxConfig;
    }

    public void read() {
        accessTokenResult = getWxAccessTokenResult(this.code);
        if (accessTokenResult.getAccessToken() == null) {
            return;
        }
        userInfo = getWxUserInfo(accessTokenResult);
    }

    public boolean isSuccess() {
        return accessTokenResult.getAccessToken() != null && userInfo.getNickname() != null;
    }

    public String openId(){
        return accessTokenResult.getOpenid();
    }

    private WxUserInfo getWxUserInfo(WxAccessTokenResult wxAccessTokenResult) {
        HttpResponse response = httpClient.execute(new HttpRequest("https://api.weixin.qq.com/sns/userinfo?access_token=" + wxAccessTokenResult.getAccessToken() + "&openid=" + wxAccessTokenResult.getOpenid()));
        return new Gson().fromJson(response.getBody(), WxUserInfo.class);
    }

    private WxAccessTokenResult getWxAccessTokenResult(String code) {
        HttpResponse response = httpClient.execute(new HttpRequest("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wxConfig.getAppId() + "&secret=" + wxConfig.getAppSecret() + "&code=" + code + "&grant_type=authorization_code"));
        return new Gson().fromJson(response.getBody(), WxAccessTokenResult.class);
    }

    public String nickname() {
        return userInfo.getNickname();
    }
}
