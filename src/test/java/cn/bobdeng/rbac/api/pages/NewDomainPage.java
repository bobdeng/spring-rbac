package cn.bobdeng.rbac.api.pages;

import cn.bobdeng.rbac.api.WebDriverHandler;
import cn.bobdeng.rbac.domain.Tenant;
import org.openqa.selenium.By;

import static cn.bobdeng.rbac.api.WebDriverHandler.WEBDRIVER;

public class NewDomainPage extends BasePage {
    public NewDomainPage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }

    public void inputDomain(String domain) {
        inputById(domain, "inputName");
    }

    public void submit() {
        WEBDRIVER.findElement(By.id("buttonSubmit")).submit();
    }
}
