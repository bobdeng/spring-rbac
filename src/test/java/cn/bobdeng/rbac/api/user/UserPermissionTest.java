package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.api.E2ETest;
import cn.bobdeng.rbac.api.UserWithTenantFixture;
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
    public void should_can_visit_function_permission_granted() throws Exception {
        TestPermissionGrantedPage page = new TestPermissionGrantedPage(webDriverHandler);
        page.open();
        assertTrue(page.hasText("permission_granted"));
    }
}
