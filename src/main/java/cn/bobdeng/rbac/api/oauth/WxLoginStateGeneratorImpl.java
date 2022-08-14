package cn.bobdeng.rbac.api.oauth;

import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class WxLoginStateGeneratorImpl implements WxLoginStateGenerator {
    private RedissonClient redissonClient;

    public WxLoginStateGeneratorImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public String generate() {
        String result = UUID.randomUUID().toString();
        redissonClient.getBucket(result).set(System.currentTimeMillis(), 5, TimeUnit.MINUTES);
        return result;
    }

    @Override
    public boolean verify(String code) {
        return Optional.ofNullable(redissonClient.getBucket(code).get()).isPresent();
    }
}
