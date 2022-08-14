package cn.bobdeng.rbac;

import cn.bobdeng.rbac.api.WebDriverHandler;
import cn.bobdeng.rbac.api.pages.BasePage;
import org.openqa.selenium.By;

public class JsonPage extends BasePage {
    public JsonPage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }

    public void open(String url) {
        webDriverHandler.open(url);
    }

    public String content() {
        return WebDriverHandler.WEBDRIVER.findElement(By.tagName("body")).getText();
    }
}
