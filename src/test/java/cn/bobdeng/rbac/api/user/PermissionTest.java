package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.security.Permission;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PermissionTest {

    public static void assertPermissionAnnotation(Class<?> clz, String methodName, String[] expectedPermissions) {
        Method newUser = Arrays.stream(clz.getDeclaredMethods())
                .filter(method -> method.getName().equals(methodName)).findFirst().get();
        assertTrue(newUser.isAnnotationPresent(Permission.class));
        Permission annotation = newUser.getAnnotation(Permission.class);
        assertArrayEquals(expectedPermissions, annotation.allows());
    }
}
