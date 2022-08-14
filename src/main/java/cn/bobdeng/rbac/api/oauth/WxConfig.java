package cn.bobdeng.rbac.api.oauth;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ToString
public class WxConfig {
    @Value(value = "${wx.appId:null}")
    private String appId;
    @Value(value = "${wx.appSecret:null}")
    private String appSecret;
    @Value(value = "${wx.callback:null}")
    private String callback;
    @Value(value = "${wx.enabled:false}")
    private boolean enabled;
}
