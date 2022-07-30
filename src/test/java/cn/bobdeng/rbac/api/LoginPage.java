package cn.bobdeng.rbac.api;

public class LoginPage {
    private WebDriverHandler webDriverHandler;

    public LoginPage(WebDriverHandler webDriver) {
        this.webDriverHandler = webDriver;
    }

    public void open() {
        webDriverHandler.open("/rbac/login");
    }
}
