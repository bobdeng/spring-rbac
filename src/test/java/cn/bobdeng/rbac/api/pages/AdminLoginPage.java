package cn.bobdeng.rbac.api.pages;

import cn.bobdeng.rbac.api.WebDriverHandler;
import org.openqa.selenium.By;

import static cn.bobdeng.rbac.api.WebDriverHandler.WEBDRIVER;

public class AdminLoginPage extends BasePage {

    public AdminLoginPage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }


    public void open() {
        this.webDriverHandler.open("/admin/");
    }

    public void loginWith(String password) throws InterruptedException {
        WEBDRIVER.findElement(By.id("inputPassword")).sendKeys(password);
        WEBDRIVER.findElement(By.id("buttonLogin")).click();
        while (true) {
            Thread.sleep(10);
            try {
                WebDriverHandler.WEBDRIVER.findElement(By.className("anticon-spin"));
            } catch (Exception e) {
                break;
            }
        }
    }

    public String error() {
        return WEBDRIVER.findElement(By.id("error")).getText();
    }

    public void setCookie(String name, String value) {
        webDriverHandler.addCookie(name, value);
    }
}
