package cn.bobdeng.rbac.api.oauth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WxConfigResult {
    private String appId;
    private String callback;

    public WxConfigResult(WxConfig wxConfig) {
        this(wxConfig.getAppId(), wxConfig.getCallback());
    }
}
