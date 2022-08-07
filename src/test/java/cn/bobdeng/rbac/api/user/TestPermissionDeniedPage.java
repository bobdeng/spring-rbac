package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.WebDriverHandler;
import cn.bobdeng.rbac.api.pages.BasePage;

public class TestPermissionDeniedPage extends BasePage {
    public TestPermissionDeniedPage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }

    public void open() {
        webDriverHandler.open("/test_permission_denied");
    }
}
