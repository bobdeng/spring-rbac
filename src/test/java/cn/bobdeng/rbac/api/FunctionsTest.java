package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.domain.function.Function;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FunctionsTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_list_functions() throws Exception {
        Function[] functions = getFunctions();
        assertTrue(functions.length > 0);
    }

    private Function[] getFunctions() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/functions"))
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Function[] functions = new Gson().fromJson(content, Function[].class);
        return functions;
    }

    @Test
    public void should_append_function() throws Exception {
        Function[] functions = getFunctions();
        Function last = functions[functions.length - 1];
        assertEquals("out1", last.getKey());
    }
}
