package cn.bobdeng.rbac.api.pages;

import cn.bobdeng.rbac.api.WebDriverHandler;
import org.openqa.selenium.By;

import static cn.bobdeng.rbac.api.WebDriverHandler.WEBDRIVER;

public class NewTenantPage {
    private WebDriverHandler webDriverHandler;

    public NewTenantPage(WebDriverHandler webDriverHandler) {

        this.webDriverHandler = webDriverHandler;
    }

    public void open() {
        webDriverHandler.open("/rbac/admin/console/new_tenant");
    }

    public void inputTenantName(String name) {
        WEBDRIVER.findElement(By.id("inputName")).sendKeys(name);
    }

    public void submit() {
        WEBDRIVER.findElement(By.id("buttonSubmit")).submit();
    }
}
