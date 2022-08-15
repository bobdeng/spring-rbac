package cn.bobdeng.rbac.utils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestHttpClientController {
    @GetMapping("/test_http_client")
    public String test() {
        return "hello";
    }
}
