package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.WebDriverHandler;
import cn.bobdeng.rbac.api.pages.BasePage;
import org.openqa.selenium.By;

public class SetRolePage extends BasePage {
    public SetRolePage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }

    public void isRoleChecked() {
        waitUntil(() -> webDriverHandler.getWebDriver().findElement(By.xpath("//input[@type='checkbox']")).isSelected(), 1000);
    }

    public void clickRole() {
        webDriverHandler.getWebDriver().findElement(By.xpath("//input[@type='checkbox']")).click();
    }

    public void save() {
        clickButton("保 存");
    }
}
