package cn.bobdeng.rbac.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class SessionControllerTest {
    @Autowired
    MockMvc mockMvc;


    @Test
    public void get_session() throws Exception {
        mockMvc.perform(get("/rbac/user/session"))
                .andExpect(status().isOk());
    }
    @Test
    public void admin_login_with_right_password(){

    }

}