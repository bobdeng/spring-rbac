package cn.bobdeng.rbac.api.pages;

import cn.bobdeng.rbac.api.WebDriverHandler;
import cn.bobdeng.rbac.domain.Tenant;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.bobdeng.rbac.api.WebDriverHandler.WEBDRIVER;

public class ListTenantDomainPage extends BasePage {
    public ListTenantDomainPage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }

    public void open(Tenant tenant) {
        webDriverHandler.open("/rbac/admin/console/tenant/" + tenant.identity() + "/domains");
    }

    public List<Map<String, String>> domains() {
        return WEBDRIVER.findElement(By.id("tableDomains"))
                .findElement(By.tagName("tbody"))
                .findElements(By.tagName("tr"))
                .stream().map(webElement -> {
                    HashMap<String, String> result = new HashMap<>();
                    result.put("域名", webElement.findElements(By.tagName("td")).get(0).getText());
                    return result;
                }).collect(Collectors.toList());
    }

    public String newDomainLink() {
        return WEBDRIVER.findElement(By.id("linkNewDomain")).getAttribute("href");
    }
}
