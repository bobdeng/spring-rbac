package cn.bobdeng.rbac.api.role;

import cn.bobdeng.rbac.api.WebDriverHandler;
import cn.bobdeng.rbac.api.pages.BasePage;

public class EditRolePage extends BasePage {
    public EditRolePage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }

    public String name() {
        return getInputValueById("inputName");
    }

}
