package cn.bobdeng.rbac.api.oauth;

import cn.bobdeng.rbac.api.WebDriverHandler;
import cn.bobdeng.rbac.api.pages.BasePage;

public class WxCallbackPage extends BasePage {
    public WxCallbackPage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }

    public void open(String code, String state) {
        webDriverHandler.open("/wx_callback?code="+code+"&state="+state);
    }
}
