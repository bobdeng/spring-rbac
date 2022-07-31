package cn.bobdeng.rbac.api.pages;

import cn.bobdeng.rbac.api.WebDriverHandler;

public class BasePage {
    protected WebDriverHandler webDriverHandler;

    public BasePage(WebDriverHandler webDriverHandler) {
        this.webDriverHandler = webDriverHandler;
    }
}
