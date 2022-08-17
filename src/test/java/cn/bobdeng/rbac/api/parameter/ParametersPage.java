package cn.bobdeng.rbac.api.parameter;

import cn.bobdeng.rbac.api.WebDriverHandler;
import cn.bobdeng.rbac.api.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static cn.bobdeng.rbac.api.WebDriverHandler.WEBDRIVER;

public class ParametersPage extends BasePage {
    public ParametersPage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }

    public void open() {
        webDriverHandler.open("/admin/#/parameters");
    }

    public String parameterValue(int index) {
        WebElement input = WEBDRIVER.findElements(By.tagName("input")).get(index);
        return input.getAttribute("value");
    }

    public void inputValue(int index, String content) {
        WEBDRIVER.findElements(By.tagName("input")).get(index)
                .clear();
        WEBDRIVER.findElements(By.tagName("input")).get(index)
                .sendKeys(content);
    }

    public void save() {
       clickById("buttonSave");
    }
}
