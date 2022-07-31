package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.Cookies;
import cn.bobdeng.rbac.api.pages.AdminLoginPage;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class E2ETest {
    @Autowired
    WebDriverHandler webDriverHandler;
    @Autowired
    JdbcTemplate jdbcTemplate;

    protected void clearTable(String tableName) {
        jdbcTemplate.execute("truncate table " + tableName);
    }

    protected void adminLogin() {
        AdminLoginPage adminLoginPage = new AdminLoginPage(webDriverHandler);
        adminLoginPage.open();
        adminLoginPage.setCookie(Cookies.ADMIN_AUTHORIZATION, new AdminToken().toString());
    }
}
