package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.WebDriverHandler;
import cn.bobdeng.rbac.api.pages.BasePage;

public class TestPermissionGrantedPage extends BasePage {
    public TestPermissionGrantedPage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }

    public void open() {
        webDriverHandler.open("/test_permission_granted");
    }
}
