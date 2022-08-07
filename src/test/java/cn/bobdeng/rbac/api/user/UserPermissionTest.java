package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.E2ETest;
import cn.bobdeng.rbac.api.UserWithTenantFixture;
import cn.bobdeng.rbac.api.WebDriverHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class UserPermissionTest extends E2ETest {
    @Autowired
    UserWithTenantFixture userWithTenantFactory;

    @BeforeEach
    public void setup() {
        userWithTenantFactory.init();
        userLogin(userWithTenantFactory.user());
    }

    @Test
    public void should_can_visit_function_permission_granted() {
        TestPermissionGrantedPage page = new TestPermissionGrantedPage(webDriverHandler);
        page.open();
        assertTrue(page.hasText("permission_granted"));
    }

    @Test
    public void should_not_can_visit_function_permission_denied() {
        TestPermissionDeniedPage page = new TestPermissionDeniedPage(webDriverHandler);
        page.open();
        assertTrue(page.hasText("无权限"));
    }
}
