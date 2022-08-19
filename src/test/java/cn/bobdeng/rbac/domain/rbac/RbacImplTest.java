package cn.bobdeng.rbac.domain.rbac;

import cn.bobdeng.rbac.archtype.FieldIllegalException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RbacImplTest {
    @Test
    public void should_throw_when_login_name_shorter_than_3() {
        RbacImpl rbac = new RbacImpl();

        assertThrows(FieldIllegalException.class, () -> rbac.addLoginName(new LoginNameDescription("ab", 1)));

    }
    @Test
    public void should_throw_when_login_name_longer_than_20() {
        RbacImpl rbac = new RbacImpl();

        assertThrows(FieldIllegalException.class, () -> rbac.addLoginName(new LoginNameDescription("abcee12345abcde123451", 1)));

    }
}