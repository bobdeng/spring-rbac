package cn.bobdeng.rbac.api;

import static cn.bobdeng.rbac.api.WebDriverHandler.WEBDRIVER;

public class AdminConsolePage {
    private WebDriverHandler webDriverHandler;

    public AdminConsolePage(WebDriverHandler webDriverHandler) {

        this.webDriverHandler = webDriverHandler;
    }

    public void open() {
        webDriverHandler.open("/admin/console/home");
    }

    public String content() {
        return WEBDRIVER.getPageSource();
    }
}
