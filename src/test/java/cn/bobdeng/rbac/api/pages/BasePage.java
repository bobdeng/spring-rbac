package cn.bobdeng.rbac.api.pages;

import cn.bobdeng.rbac.api.WebDriverHandler;
import org.openqa.selenium.By;

import static cn.bobdeng.rbac.api.WebDriverHandler.WEBDRIVER;

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

    public void clickButton(String title) {
        WEBDRIVER.findElement(By.xpath("//button//span[normalize-space()='" + title + "']"))
                .click();
    }

    public void clickContent(String content) {
        WEBDRIVER.findElement(By.xpath("//span[normalize-space()='" + content + "']"))
                .click();
    }

    public void clickById(String id) {
        WEBDRIVER.findElement(By.id(id)).click();
    }

    public void inputById(String content, String id) {
        WEBDRIVER.findElement(By.id(id)).sendKeys(content);
    }

    public boolean hasNoData() {
        return WEBDRIVER.getPageSource().contains("No Data");
    }

    public boolean hasText(String text) {
        return WEBDRIVER.getPageSource().contains(text);
    }

    protected String getInputValueById(String id) {
        return WEBDRIVER.findElement(By.id(id)).getAttribute("value");
    }
}
