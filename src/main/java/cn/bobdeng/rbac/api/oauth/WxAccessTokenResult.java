package cn.bobdeng.rbac.api.oauth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WxAccessTokenResult {
    private String accessToken;
    private String openid;

    public WxAccessTokenResult(String accessToken, String openid) {
        this.accessToken = accessToken;
        this.openid = openid;
    }
}
