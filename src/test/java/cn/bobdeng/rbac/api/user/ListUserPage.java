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

    public void clickUnbind() {
        clickById("buttonRemove");
    }

    public void inputLoginName(String bobdeng) {
        inputById(bobdeng, "inputLoginName");
    }

    public void clickBind() {
        clickById("buttonAdd");
    }

    public void clickSetRole() {
        waitUntil(() -> hasText("角色"), 1000);
        clickContent("角色");
    }
}
