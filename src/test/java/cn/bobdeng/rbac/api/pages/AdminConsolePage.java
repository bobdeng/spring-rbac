package cn.bobdeng.rbac.api.pages;

import cn.bobdeng.rbac.api.WebDriverHandler;

import static cn.bobdeng.rbac.api.WebDriverHandler.WEBDRIVER;

public class AdminConsolePage {
    private WebDriverHandler webDriverHandler;

    public AdminConsolePage(WebDriverHandler webDriverHandler) {

        this.webDriverHandler = webDriverHandler;
    }

    public void open() {
        webDriverHandler.open("/rbac/admin/console/home");
    }

    public String content() {
        return WEBDRIVER.getPageSource();
    }

    public String title() {
        return WEBDRIVER.getTitle();
    }
}
