package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.WebDriverHandler;
import cn.bobdeng.rbac.api.pages.BasePage;

public class ListUserPage extends BasePage {
    public ListUserPage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }

    public void open() {
        webDriverHandler.open("/admin/#/users");
    }
}
