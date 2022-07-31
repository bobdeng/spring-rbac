package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.api.pages.LoginPage;
import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.server.dao.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoginPageTest extends E2ETest {
    @Autowired
    WebDriverHandler webDriverHandler;
    @Autowired
    DomainRepository domainRepository;
    @Autowired
    TenantRepository tenantRepository;
    @Autowired
    DomainDAO domainDAO;
    @Autowired
    TenantDAO tenantDAO;
    private LoginPage loginPage;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserDAO userDAO;
    @Autowired
    PasswordDAO passwordDAO;
    @Autowired
    LoginNameDAO loginNameDAO;
    private Tenant tenant;

    @BeforeEach
    public void setup() {
        jdbcTemplate.execute("truncate t_rbac_domain");
        jdbcTemplate.execute("truncate t_rbac_tenant");
        jdbcTemplate.execute("truncate t_rbac_user");
        jdbcTemplate.execute("truncate t_rbac_password");
        jdbcTemplate.execute("truncate t_rbac_login_name");
        tenant = tenantRepository.save(new Tenant(new TenantDescription("租户1")));
        domainRepository.save(new Domain(new DomainDescription("localhost", tenant.getId())));
        assertNotNull(webDriverHandler.WEBDRIVER);
        loginPage = new LoginPage(webDriverHandler);
        webDriverHandler.removeAllCookies();
    }

    @Test
    public void should_show_tenant_name() {
        loginPage.open("/");
        assertEquals("登录", loginPage.title());
        assertEquals("租户1", loginPage.tenantName());
        assertEquals("租户1", loginPage.tenantNameInputValue());
        assertEquals("/", loginPage.fromValue());
    }

    @Test
    public void should_login_fail() {
        loginPage.open("/");
        loginPage.inputLoginName("bob");
        loginPage.inputPassword("123456");
        loginPage.submit();
        assertEquals("登录失败", loginPage.error());
    }

    @Test
    public void should_go_back_when_login_success() {
        UserDO user = userDAO.save(UserDO.builder()
                .name("bob").tenantId(tenant.identity())
                .build());
        loginNameDAO.save(LoginNameDO.builder()
                .loginName("bob")
                .tenantId(tenant.getId())
                .userId(user.getId())
                .build());
        String encodedPassword = new BCryptPasswordEncoder().encode("123456");
        passwordDAO.save(new Password(user.getId(), new PasswordDescription(encodedPassword)));
        loginPage.open("/");
        loginPage.inputLoginName("bob");
        loginPage.inputPassword("123456");
        loginPage.submit();
        assertEquals("登录成功", loginPage.error());
        assertEquals("1; url=/", loginPage.refreshUrl());
        Cookie authorization = webDriverHandler.getCookie("Authorization");
        assertNotNull(authorization);
        LoginToken loginToken = LoginToken.decode(authorization.getValue());
        assertEquals(new LoginToken(user.toUser(tenantRepository)), loginToken);
    }


}
