package cn.bobdeng.rbac.api.pages;

import cn.bobdeng.rbac.api.WebDriverHandler;
import org.openqa.selenium.By;

public class BasePage {
    protected WebDriverHandler webDriverHandler;

    public BasePage(WebDriverHandler webDriverHandler) {
        this.webDriverHandler = webDriverHandler;
    }

    public void waitUntilNoButtonSpin() {
        waitClassDisappeared("anticon-spin");
    }

    public void waitUntilNoSpin() {
        waitClassDisappeared("ant-spin-spinning");
    }

    private void waitClassDisappeared(String className) {
        while (true) {
            try {
                Thread.sleep(10);
                WebDriverHandler.WEBDRIVER.findElement(By.className(className));
            } catch (Exception e) {
                break;
            }
        }
    }
}
