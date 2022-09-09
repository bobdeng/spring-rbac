package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.ClearTable;
import cn.bobdeng.rbac.Cookies;
import cn.bobdeng.rbac.api.pages.AdminLoginPage;
import cn.bobdeng.rbac.domain.rbac.User;
import okhttp3.OkHttpClient;
import org.junit.runner.RunWith;
import org.openqa.selenium.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.util.function.Supplier;

import static cn.bobdeng.rbac.api.WebDriverHandler.WEBDRIVER;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class E2ETest {
    @Autowired
    protected WebDriverHandler webDriverHandler;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected ClearTable clearTable;
    protected OkHttpClient okHttpClient;

    @PostConstruct
    public void init() {
        okHttpClient = new OkHttpClient.Builder().build();
    }

    protected void adminLogin() {
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.open();
        webDriverHandler.removeAllCookies();
        adminLoginPage.setCookie(Cookies.ADMIN_AUTHORIZATION, new AdminToken().toString());
    }

    protected void userLogin(User user) {
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.open();
        webDriverHandler.removeAllCookies();
        adminLoginPage.setCookie(Cookies.AUTHORIZATION, new UserToken(user).toTokenString());
    }
    protected void loginWithErrorToken() {
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.open();
        webDriverHandler.removeAllCookies();
        adminLoginPage.setCookie(Cookies.AUTHORIZATION,"notatoken");
    }

    protected void clearLogin() {
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.open();
        webDriverHandler.removeAllCookies();
    }

    public void waitUntil(Supplier<Boolean> check, int time) {
        long begin = System.currentTimeMillis();
        while (System.currentTimeMillis() - begin < time) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (check.get())
                return;
        }
        fail();
    }

    public Cookie getCookie(String name) {
        return WEBDRIVER.manage().getCookieNamed(name);
    }
}
