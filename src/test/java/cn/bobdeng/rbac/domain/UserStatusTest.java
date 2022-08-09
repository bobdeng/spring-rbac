package cn.bobdeng.rbac.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserStatusTest {
    @Test
    public void test(){
        assertEquals(User.UserStatus.Normal, User.UserStatus.of("normal"));
        assertEquals(User.UserStatus.Locked, User.UserStatus.of("locked"));
    }
}