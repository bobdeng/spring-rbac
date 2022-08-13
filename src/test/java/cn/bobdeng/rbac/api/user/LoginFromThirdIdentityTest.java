package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.ThirdLoginForm;
import cn.bobdeng.rbac.api.ThirdLoginService;
import cn.bobdeng.rbac.api.UserToken;
import cn.bobdeng.rbac.api.UserWithTenantFixture;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.server.dao.ThirdIdentityDAO;
import cn.bobdeng.rbac.server.dao.ThirdIdentityDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class LoginFromThirdIdentityTest {
    @Autowired
    UserWithTenantFixture userWithTenantFixture;
    @Autowired
    ThirdLoginService thirdLoginService;
    @Autowired
    ThirdIdentityDAO thirdIdentityDAO;
    private Tenant tenant;

    @BeforeEach
    public void setup() {
        userWithTenantFixture.init();
        tenant = userWithTenantFixture.getTenant();
    }

    @Test
    public void should_create_user_when_first_login_from_third() {
        ThirdLoginForm thirdLoginForm = new ThirdLoginForm();
        thirdLoginForm.setIdentity("13456");
        thirdLoginForm.setUserName("李四");
        thirdLoginForm.setThirdName("微信");

        UserToken token = thirdLoginService.login(tenant, thirdLoginForm);

        List<User> users = tenant.users().findByName("李四");
        assertEquals(1, users.size());
        List<ThirdIdentityDO> thirdIdentities = thirdIdentityDAO.findAllByTenantId(tenant.identity());
        assertEquals(1, thirdIdentities.size());
        assertNotNull(token);
        assertEquals(users.get(0).identity(),token.getId());
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
        UserToken token = thirdLoginService.login(tenant, thirdLoginForm);

        List<User> users = tenant.users().findByName("李四");
        assertEquals(0, users.size());
        List<ThirdIdentityDO> thirdIdentities = thirdIdentityDAO.findAllByTenantId(tenant.identity());
        assertEquals(1, thirdIdentities.size());
        assertNotNull(token);
        assertEquals(userWithTenantFixture.user().identity(),token.getId());
    }
}
