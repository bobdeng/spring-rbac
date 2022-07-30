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

    public void open(String from) {
        webDriverHandler.open("/rbac/login?from=" + from);
    }

    public String title() {
        return webDriver.getTitle();
    }

    public String tenantName() {
        return webDriver.findElement(By.id("tenantName")).getText();
    }

    public String tenantNameInputValue() {
        return webDriver.findElement(By.id("inputTenantName")).getAttribute("value");
    }

    public void inputLoginName(String loginName) {
        webDriver.findElement(By.id("inputLoginName")).sendKeys(loginName);
    }

    public void inputPassword(String password) {
        webDriver.findElement(By.id("inputPassword")).sendKeys(password);
    }

    public void submit() {
        webDriver.findElement(By.id("buttonSubmit")).submit();
    }

    public String error() {
        return webDriver.findElement(By.id("error")).getText();
    }

    public String refreshUrl() {
        return webDriver.findElements(By.tagName("meta"))
                .stream().filter(webElement -> "Refresh".equals(webElement.getAttribute("http-equiv")))
                .findFirst()
                .map(webElement -> webElement.getAttribute("content"))
                .orElse(null);
    }

    public String fromValue() {
        return webDriver.findElement(By.id("inputFrom")).getAttribute("value");
    }
}
