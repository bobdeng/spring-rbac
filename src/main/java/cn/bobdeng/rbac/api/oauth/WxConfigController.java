package cn.bobdeng.rbac.api.oauth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public record WxConfigController(WxConfig wxConfig) {

    @GetMapping("/wx_config")
    public WxConfigResult getWxConfig(HttpServletResponse response) {
        if (wxConfig.isEnabled()) {
            return new WxConfigResult(wxConfig);
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return null;
    }
}
