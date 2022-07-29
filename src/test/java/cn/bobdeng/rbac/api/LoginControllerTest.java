package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.RbacServerApplication;
import cn.bobdeng.rbac.domain.*;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RbacServerApplication.class)
@Transactional
@AutoConfigureMockMvc
class LoginControllerTest {
    @Autowired
    TenantRepository tenantRepository;
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach()
    public void setup() {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void login_success() throws Exception {
        Tenant tenant = new Tenant(new TenantDescription("租户1"));
        tenant = tenantRepository.save(tenant);
        User bob = tenant.addUser(new UserDescription("bob.deng"));
        tenant.addLoginName(new LoginNameDescription("bob", bob));
        bob.savePassword(new RawPassword("123456"));

        LoginForm loginForm = new LoginForm();
        loginForm.setTenant("租户1");
        loginForm.setLoginName("bob");
        loginForm.setPassword("123456");
        String content = new Gson().toJson(loginForm);
        MvcResult mvcResult = mockMvc.perform(post("/rbac/sessions")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();
        Cookie authorization = mvcResult.getResponse().getCookie("Authorization");
        assertNotNull(authorization);
    }

}