package cn.bobdeng.rbac.server.impl.rbac;

import cn.bobdeng.rbac.api.E2ETest;
import cn.bobdeng.rbac.archtype.SystemDate;
import cn.bobdeng.rbac.domain.rbac.Lock;
import cn.bobdeng.rbac.domain.rbac.LockDescription;
import cn.bobdeng.rbac.domain.rbac.User;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class UserLockImplTest extends E2ETest {
    @Autowired
    RedissonClient redissonClient;

    @Test
    public void should_save_lock() {
        SystemDate.setNowSupplier("2020-01-01 10:00:00");
        User user = new User(100, null);
        UserLockImpl userLock = new UserLockImpl(user, redissonClient);
        userLock.save(new Lock(user.identity(), new LockDescription()));

        Object lock = getBucket(user).get();
        assertNotNull(lock);
        assertEquals(lock, 1577844000000L);
    }

    private RBucket<Object> getBucket(User user) {
        return redissonClient.getBucket("rbac-login-lock-" + user.identity());
    }

    @Test
    public void should_get_lock() {
        User user = new User(100, null);
        UserLockImpl userLock = new UserLockImpl(user, redissonClient);
        getBucket(user).set(1577844000000L, 2, TimeUnit.SECONDS);

        Lock lock = userLock.findByIdentity(user.identity()).get();
        assertEquals(lock.description().getLockAt(), 1577844000000L);
    }

    @Test
    public void should_get_lock_when_no_lock() {
        User user = new User(100, null);
        UserLockImpl userLock = new UserLockImpl(user, redissonClient);
        getBucket(user).delete();

        assertTrue(userLock.findByIdentity(user.identity()).isEmpty());
    }
}