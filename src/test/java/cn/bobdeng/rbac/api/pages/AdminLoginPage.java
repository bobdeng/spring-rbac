package cn.bobdeng.rbac.api.pages;

import cn.bobdeng.rbac.api.WebDriverHandler;
import org.openqa.selenium.By;

import static cn.bobdeng.rbac.api.WebDriverHandler.WEBDRIVER;

public class AdminLoginPage {
    private WebDriverHandler webDriverHandler;

    public AdminLoginPage(WebDriverHandler webDriverHandler) {

        this.webDriverHandler = webDriverHandler;
    }

    public void open() {
        this.webDriverHandler.open("/rbac/admin/login");
    }

    public void loginWith(String password) {
        WEBDRIVER.findElement(By.id("inputPassword")).sendKeys(password);
        WEBDRIVER.findElement(By.id("buttonSubmit")).submit();
    }

    public String error() {
        return WEBDRIVER.findElement(By.id("error")).getText();
    }

    public void setCookie(String name, String value) {
        webDriverHandler.addCookie(name, value);
    }
}
