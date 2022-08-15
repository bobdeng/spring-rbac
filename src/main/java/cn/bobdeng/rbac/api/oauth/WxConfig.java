package cn.bobdeng.rbac.api.oauth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class WxConfig {
    @Value(value = "${wx.appId:null}")
    @Setter
    private String appId;
    @Value(value = "${wx.appSecret:null}")
    private String appSecret;
    @Value(value = "${wx.callback:null}")
    @Setter
    private String callback;
    @Value(value = "${wx.enabled:false}")
    @Setter
    private boolean enabled;

}
