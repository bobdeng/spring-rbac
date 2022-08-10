package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.ClearTable;
import cn.bobdeng.rbac.Cookies;
import cn.bobdeng.rbac.api.pages.AdminLoginPage;
import cn.bobdeng.rbac.domain.User;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class E2ETest {
    @Autowired
    protected WebDriverHandler webDriverHandler;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ClearTable clearTable;

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
        adminLoginPage.setCookie(Cookies.AUTHORIZATION, new UserToken(user).toString());
    }

}
