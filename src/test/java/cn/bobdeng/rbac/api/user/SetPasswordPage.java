package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.WebDriverHandler;
import cn.bobdeng.rbac.api.pages.BasePage;

public class SetPasswordPage extends BasePage {
    public SetPasswordPage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }

    public void inputCurrentPassword(String content) {
        inputById(content, "inputCurrentPassword");
    }

    public void inputNewPassword(String content) {
        inputById(content, "inputNewPassword");
    }

    public void inputConfirmation(String content) {
        inputById(content, "inputConfirmation");
    }

    public void save() {
        clickButton("确 定");
    }

    public void open() {
        webDriverHandler.open("/admin/#/set_password");
    }
}
