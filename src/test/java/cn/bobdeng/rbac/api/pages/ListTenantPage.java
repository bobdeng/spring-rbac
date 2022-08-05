package cn.bobdeng.rbac.api.pages;

import cn.bobdeng.rbac.api.WebDriverHandler;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.bobdeng.rbac.api.WebDriverHandler.WEBDRIVER;

public class ListTenantPage extends BasePage {
    public ListTenantPage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }

    public void open() {
        webDriverHandler.open("/admin/#/tenants");
    }

    public List<Map<String, String>> tenants() {
        return WEBDRIVER.findElement(By.id("tableTenants"))
                .findElement(By.tagName("tbody"))
                .findElements(By.tagName("tr"))
                .stream()
                .map(element -> {
                    Map<String, String> values = new HashMap<>();
                    values.put("租户名", element.findElements(By.tagName("td")).get(0).getText());
                    //values.put("域名链接", element.findElements(By.tagName("td")).get(1).findElement(By.tagName("a")).getAttribute("href"));
                    return values;
                })
                .collect(Collectors.toList());
    }

    public String totalElements() {
        return WEBDRIVER.findElement(By.id("textTotalElements")).getText();
    }

    public String page() {
        return WEBDRIVER.findElement(By.id("textPage")).getText();
    }

    public String newLink() {
        return WEBDRIVER.findElement(By.id("linkNewTenant")).getAttribute("href");

    }

    public void search(String name) {
        WEBDRIVER.findElement(By.id("inputSearchName")).sendKeys(name);
        WEBDRIVER.findElement(By.id("buttonSubmit")).submit();
    }

    public boolean hasNoData() {
        return WEBDRIVER.getPageSource().contains("No Data");
    }
}
