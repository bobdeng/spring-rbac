package cn.bobdeng.rbac.server.impl.rbac;

import cn.bobdeng.rbac.domain.rbac.Lock;
import cn.bobdeng.rbac.domain.rbac.LockDescription;
import cn.bobdeng.rbac.domain.rbac.User;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class UserLockImpl implements User.UserLock {
    private User user;
    private RedissonClient redissonClient;

    public UserLockImpl(User user, RedissonClient redissonClient) {
        this.user = user;
        this.redissonClient = redissonClient;
    }

    @Override
    public Lock save(Lock entity) {
        RBucket<Object> bucket = getBucket(entity.identity());
        bucket.set(entity.description().getLockAt(), 2, TimeUnit.SECONDS);
        return entity;
    }

    private RBucket<Object> getBucket(Integer identity) {
        return redissonClient.getBucket("rbac-login-lock-" + identity);
    }

    @Override
    public Optional<Lock> findByIdentity(Integer integer) {
        return Optional.ofNullable(getBucket(integer).get())
                .map(value -> new Lock(integer, new LockDescription((long) value)));
    }
}
