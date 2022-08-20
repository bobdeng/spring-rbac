package cn.bobdeng.rbac.api.oauth;

import cn.bobdeng.rbac.api.E2ETest;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class WxLoginStateGeneratorImplTest extends E2ETest {
    @Autowired
    WxLoginStateGeneratorImpl wxLoginStateGenerator;
    @Autowired
    RedissonClient redissonClient;

    @Test
    public void should_store_when_generate() {
        String state = wxLoginStateGenerator.generate();
        assertNotNull(state);
        Long value = (Long) redissonClient.getBucket(state).get();
        assertTrue(value - System.currentTimeMillis() < 100);
    }

    @Test
    public void should_verify_pass_when_has_value() {
        String state = "123456";
        redissonClient.getBucket(state).set(System.currentTimeMillis(), 1, TimeUnit.MINUTES);
        assertTrue(wxLoginStateGenerator.verify(state));
    }

    @Test
    public void should_verify_fail_when_has_no_value() {
        assertFalse(wxLoginStateGenerator.verify("state_not_exist"));
    }
}