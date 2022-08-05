package cn.bobdeng.rbac.api.pages;

import cn.bobdeng.rbac.api.WebDriverHandler;

import static cn.bobdeng.rbac.api.WebDriverHandler.WEBDRIVER;

public class TestNeedAdminPage extends BasePage {

    public TestNeedAdminPage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }

    public void open() {
        webDriverHandler.open("/test_need_admin");
    }

    public String content() {
        return WEBDRIVER.getPageSource();
    }

    public String title() {
        return WEBDRIVER.getTitle();
    }
}
