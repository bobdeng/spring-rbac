package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.archtype.FieldIllegalException;
import cn.bobdeng.rbac.domain.rbac.RbacImpl;
import cn.bobdeng.rbac.domain.rbac.RoleDescription;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TenantsRoleTest {
    @Test
    public void should_throw_when_has_no_name() {
        FieldIllegalException e = assertThrows(FieldIllegalException.class, () ->
                new RbacImpl().newRole(new RoleDescription(null, Arrays.asList("role.create"))));
        assertEquals(1, e.getErrors().size());
    }

    @Test
    public void should_throw_when_has_name_empty() {
        FieldIllegalException e = assertThrows(FieldIllegalException.class, () ->
                new RbacImpl().newRole(new RoleDescription("", Arrays.asList("role.create"))));
        assertEquals(1, e.getErrors().size());
    }

    @Test
    public void should_throw_when_name_size_exceed_21() {
        FieldIllegalException e = assertThrows(FieldIllegalException.class, () ->
                new RbacImpl().newRole(new RoleDescription("01234567890123456789多", Arrays.asList("role.create"))));
        assertEquals(1, e.getErrors().size());
        assertEquals("name", e.getErrors().get(0).getField());
        assertEquals("角色名不能大于20位", e.getErrors().get(0).getError());
    }

    @Test
    public void should_throw_when_allowed_empty() {
        FieldIllegalException e = assertThrows(FieldIllegalException.class, () ->
                new RbacImpl().newRole(new RoleDescription("角色名", Collections.emptyList())));
        assertEquals(1, e.getErrors().size());
    }


}
