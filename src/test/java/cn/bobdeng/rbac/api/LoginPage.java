package cn.bobdeng.rbac.api;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver webDriver;
    private By titleBy = By.tagName("title");
    private WebDriverHandler webDriverHandler;

    public LoginPage(WebDriverHandler webDriverHandler) {
        this.webDriverHandler = webDriverHandler;
        this.webDriver = WebDriverHandler.WEBDRIVER;
    }

    public void open() {
        webDriverHandler.open("/rbac/login");
    }

    public String title() {
        return webDriver.getTitle();
    }

    public String tenantName() {
        return webDriver.findElement(By.id("tenantName")).getText();
    }
}
