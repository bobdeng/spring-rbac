package cn.bobdeng.rbac.api.pages;

import cn.bobdeng.rbac.api.WebDriverHandler;
import org.openqa.selenium.By;

import static cn.bobdeng.rbac.api.WebDriverHandler.WEBDRIVER;

public class NewTenantPage extends BasePage {

    public NewTenantPage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }


    public void inputTenantName(String name) {
        inputById(name, "inputName");
    }

    public void submit() {
        String title = "确 定";
        clickButton(title);
    }

}
