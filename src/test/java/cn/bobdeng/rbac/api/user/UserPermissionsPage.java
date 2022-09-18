package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.WebDriverHandler;
import cn.bobdeng.rbac.api.pages.BasePage;

public class UserPermissionsPage extends BasePage {
    public UserPermissionsPage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }

    public void open() {
        webDriverHandler.open("/api/1.0/permissions");
    }
}
