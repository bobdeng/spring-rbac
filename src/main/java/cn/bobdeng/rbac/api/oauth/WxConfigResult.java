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

    public WxConfigResult(WxConfig wxConfig) {
        this(wxConfig.getAppId(), wxConfig.getCallback(), UUID.randomUUID().toString());
    }
}
