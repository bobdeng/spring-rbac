package cn.bobdeng.rbac.api.organization;

import cn.bobdeng.rbac.api.WebDriverHandler;
import cn.bobdeng.rbac.api.pages.BasePage;

public class OrganizationsPage extends BasePage {
    public OrganizationsPage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }

    public void open() {
        webDriverHandler.open("/admin/#/organizations");
    }

    public void inputName(String name) {
        inputById(name, "inputName");
    }

    public void save() {
        clickContent("确 定");
    }

    public void clickAdd() {
        clickButton("添加下级单位");
    }
}
