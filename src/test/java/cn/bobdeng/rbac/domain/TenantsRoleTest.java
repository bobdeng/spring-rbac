package cn.bobdeng.rbac.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TenantsRoleTest {
    @Test
    public void should_throw_when_has_no_name() {
        Tenant tenant = new Tenant();
        FieldIllegalException e = assertThrows(FieldIllegalException.class, () ->
                tenant.newRole(new RoleDescription(null, Arrays.asList("role.create"))));
        assertEquals(1, e.getErrors().size());
    }

    @Test
    public void should_throw_when_has_name_empty() {
        Tenant tenant = new Tenant();
        FieldIllegalException e = assertThrows(FieldIllegalException.class, () ->
                tenant.newRole(new RoleDescription("", Arrays.asList("role.create"))));
        assertEquals(1, e.getErrors().size());
    }

    @Test
    public void should_throw_when_name_size_exceed_21() {
        Tenant tenant = new Tenant();
        FieldIllegalException e = assertThrows(FieldIllegalException.class, () ->
                tenant.newRole(new RoleDescription("01234567890123456789多", Arrays.asList("role.create"))));
        assertEquals(1, e.getErrors().size());
        assertEquals("name", e.getErrors().get(0).getField());
        assertEquals("角色名不能大于20位", e.getErrors().get(0).getError());
    }

    @Test
    public void should_throw_when_allowed_empty() {
        Tenant tenant = new Tenant();
        FieldIllegalException e = assertThrows(FieldIllegalException.class, () ->
                tenant.newRole(new RoleDescription("角色名", Collections.emptyList())));
        assertEquals(1, e.getErrors().size());
    }
}
