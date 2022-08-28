package cn.bobdeng.rbac.api.organization;

import cn.bobdeng.rbac.api.WebDriverHandler;
import cn.bobdeng.rbac.api.pages.BasePage;

public class SelectUserPage extends BasePage {
    public SelectUserPage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }

    public void search(String keyword) {
        inputById(keyword, "search");
    }

    public void select(String name) {
        clickContent("加入");
    }
}
