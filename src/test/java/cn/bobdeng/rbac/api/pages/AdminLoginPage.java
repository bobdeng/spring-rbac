package cn.bobdeng.rbac.api.pages;

import cn.bobdeng.rbac.Cookies;
import cn.bobdeng.rbac.api.WebDriverHandler;
import org.openqa.selenium.By;

import static cn.bobdeng.rbac.api.WebDriverHandler.WEBDRIVER;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdminLoginPage extends BasePage {

    public AdminLoginPage(WebDriverHandler webDriverHandler) {
        super(webDriverHandler);
    }


    public void open() {
        this.webDriverHandler.open("/admin/");
    }

    public void loginWith(String loginName, String password) {
        inputById(password, "inputPassword");
        inputById(loginName, "inputLoginName");
        clickById("buttonLogin");
        waitUntilNoButtonSpin();
    }

    public String error() {
        return WEBDRIVER.findElement(By.id("error")).getText();
    }

    public void setCookie(String name, String value) {
        webDriverHandler.addCookie(name, value);
    }

    public void userLoginSuccess() {
        assertNotNull(WEBDRIVER.manage().getCookieNamed(Cookies.AUTHORIZATION));
    }

    public void userLoginFail() {
        waitUntil(() -> hasText("用户名或密码错误"), 1000);
    }

    public void userLocked() {
        waitUntil(() -> hasText("账号被锁定"), 1000);
    }

    public void loginByWeixin() {
        clickContent("微信登录");
    }


}
