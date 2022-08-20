package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.Cookies;
import cn.bobdeng.rbac.api.*;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.rbac.User;
import cn.bobdeng.rbac.server.dao.ThirdIdentityDAO;
import cn.bobdeng.rbac.server.dao.ThirdIdentityDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LoginFromThirdIdentityTest extends E2ETest {
    @Autowired
    UserWithTenantFixture userWithTenantFixture;
    @Autowired
    ThirdLoginService thirdLoginService;
    @Autowired
    ThirdIdentityDAO thirdIdentityDAO;
    private Tenant tenant;
    private HttpServletResponse response;
    private ArgumentCaptor<Cookie> argumentCaptor;

    @BeforeEach
    public void setup() {
        clearTable.clearTable("t_rbac_third_identity");
        userWithTenantFixture.init();
        tenant = userWithTenantFixture.getTenant();
        response = mock(HttpServletResponse.class);
        argumentCaptor = ArgumentCaptor.forClass(Cookie.class);
    }

    @Test
    public void should_create_user_when_first_login_from_third() {
        ThirdLoginForm thirdLoginForm = new ThirdLoginForm();
        thirdLoginForm.setIdentity("13456");
        thirdLoginForm.setUserName("李四");
        thirdLoginForm.setThirdName("微信");

        UserToken token = thirdLoginService.login(tenant, thirdLoginForm, response);

        List<User> users = userWithTenantFixture.getRbac().users().findByName("李四");
        assertEquals(1, users.size());
        List<ThirdIdentityDO> thirdIdentities = thirdIdentityDAO.findAllByTenantId(tenant.identity());
        assertEquals(1, thirdIdentities.size());
        assertNotNull(token);
        assertEquals(users.get(0).identity(), token.getId());
        verify(response).addCookie(argumentCaptor.capture());
        Cookie value = argumentCaptor.getValue();
        assertEquals(value.getName(), Cookies.AUTHORIZATION);
    }

    @Test
    public void should_search_user_when_second_login_from_third() {
        ThirdLoginForm thirdLoginForm = new ThirdLoginForm();
        thirdLoginForm.setIdentity("13456");
        thirdLoginForm.setUserName("李四");
        thirdLoginForm.setThirdName("微信");
        thirdIdentityDAO.save(ThirdIdentityDO.builder()
                .userId(userWithTenantFixture.user().identity())
                .thirdName("微信")
                .tenantId(tenant.identity())
                .identity(thirdLoginForm.getIdentity())
                .build());
        UserToken token = thirdLoginService.login(tenant, thirdLoginForm, response);

        List<User> users = userWithTenantFixture.getRbac().users().findByName("李四");
        assertEquals(0, users.size());
        List<ThirdIdentityDO> thirdIdentities = thirdIdentityDAO.findAllByTenantId(tenant.identity());
        assertEquals(1, thirdIdentities.size());
        assertNotNull(token);
        assertEquals(userWithTenantFixture.user().identity(), token.getId());
    }
}
