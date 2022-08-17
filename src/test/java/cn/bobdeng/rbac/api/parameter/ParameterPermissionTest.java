package cn.bobdeng.rbac.api.parameter;

import org.junit.jupiter.api.Test;

import static cn.bobdeng.rbac.api.user.PermissionTest.assertPermissionAnnotation;

public class ParameterPermissionTest {
    @Test
    public void should_set_permission() {
        assertPermissionAnnotation(ParameterController.class, "setParameters", new String[]{"parameters"});
    }
}
