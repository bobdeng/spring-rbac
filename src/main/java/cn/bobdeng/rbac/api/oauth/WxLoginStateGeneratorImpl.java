package cn.bobdeng.rbac.api.oauth;

import org.springframework.stereotype.Service;

@Service
public class WxLoginStateGeneratorImpl implements WxLoginStateGenerator {
    @Override
    public String generate() {
        return null;
    }

    @Override
    public boolean verify(String code) {
        return false;
    }
}
