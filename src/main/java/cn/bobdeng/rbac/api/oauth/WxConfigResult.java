package cn.bobdeng.rbac.api.oauth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class WxConfigResult {
    private String appId;
    private String callback;
    private String code;

    public WxConfigResult(WxConfig wxConfig, String state) {
        this(wxConfig.getAppId(), wxConfig.getCallback() + "/wx_callback", state);
    }
}
