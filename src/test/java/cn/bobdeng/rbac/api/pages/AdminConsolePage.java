package cn.bobdeng.rbac.api.pages;

import cn.bobdeng.rbac.api.WebDriverHandler;

import static cn.bobdeng.rbac.api.WebDriverHandler.WEBDRIVER;

public class AdminConsolePage extends BasePage{

    public AdminConsolePage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }

    public void open() {
        webDriverHandler.open("/api/rbac/admin/console/home");
    }

    public String content() {
        return WEBDRIVER.getPageSource();
    }

    public String title() {
        return WEBDRIVER.getTitle();
    }
}
