package cn.bobdeng.rbac.api.user;

import org.junit.jupiter.api.Test;


class UserLockControllerTest {
    @Test
    public void should_has_permission_annotation(){
        PermissionTest.assertPermissionAnnotation(UserLockController.class,"lockUser",new String[]{"user.lock"});
        PermissionTest.assertPermissionAnnotation(UserLockController.class,"unlockUser",new String[]{"user.lock"});
    }
}