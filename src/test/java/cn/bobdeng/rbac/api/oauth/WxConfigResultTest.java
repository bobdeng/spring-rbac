package cn.bobdeng.rbac.api.oauth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WxConfigResultTest {
    @Test
    public void should_get_result() {
        WxConfig wxConfig = new WxConfig();
        wxConfig.setCallback("http://test.com");
        wxConfig.setAppId("123");
        WxConfigResult configResult = new WxConfigResult(wxConfig);
        assertEquals("123", configResult.getAppId());
        assertEquals("http://test.com", configResult.getCallback());
    }
}