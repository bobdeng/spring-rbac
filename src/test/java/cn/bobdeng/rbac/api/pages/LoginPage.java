package cn.bobdeng.rbac.api.pages;

import cn.bobdeng.rbac.api.WebDriverHandler;
import org.openqa.selenium.By;

import static cn.bobdeng.rbac.api.WebDriverHandler.WEBDRIVER;

public class LoginPage extends BasePage {

    public LoginPage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }

    public void open(String from) {
        webDriverHandler.open("/rbac/login?from=" + from);
    }

    public String title() {
        return WEBDRIVER.getTitle();
    }

    public String tenantName() {
        return WEBDRIVER.findElement(By.id("tenantName")).getText();
    }

    public String tenantNameInputValue() {
        return getInputValueById("inputTenantName");
    }

    public void inputLoginName(String loginName) {
        inputById(loginName, "inputLoginName");
    }

    public void inputPassword(String password) {
        inputById(password, "inputPassword");
    }

    public void submit() {
        WEBDRIVER.findElement(By.id("buttonSubmit")).submit();
    }

    public String error() {
        return WEBDRIVER.findElement(By.id("error")).getText();
    }

    public String refreshUrl() {
        return WEBDRIVER.findElements(By.tagName("meta"))
                .stream().filter(webElement -> "Refresh".equals(webElement.getAttribute("http-equiv")))
                .findFirst()
                .map(webElement -> webElement.getAttribute("content"))
                .orElse(null);
    }

    public String fromValue() {
        return getInputValueById("inputFrom");
    }
}
