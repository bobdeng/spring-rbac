package cn.bobdeng.rbac.api.oauth;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class MockWxLoginStateGenerator implements WxLoginStateGenerator {
    @Override
    public String generate() {
        return "123456";
    }

    @Override
    public boolean verify(String code) {
        return code.equals("state000999");
    }
}
