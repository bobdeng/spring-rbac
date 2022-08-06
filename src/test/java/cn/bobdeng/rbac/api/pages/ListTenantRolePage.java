package cn.bobdeng.rbac.api.pages;

import cn.bobdeng.rbac.api.WebDriverHandler;
import cn.bobdeng.rbac.domain.Tenant;

public class ListTenantRolePage extends BasePage {
    public ListTenantRolePage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }

    public void open(Tenant tenant) {
        webDriverHandler.open("/admin/#/tenants/" + tenant.identity() + "/roles");
    }
}
