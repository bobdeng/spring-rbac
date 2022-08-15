package cn.bobdeng.rbac.api.oauth;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class WxAccessTokenResult {
    private String accessToken;
    private String openid;

    public WxAccessTokenResult(String accessToken, String openid) {
        this.accessToken = accessToken;
        this.openid = openid;
    }
}
