package cn.bobdeng.rbac.domain.cbac;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContextAuthorityTest {
    @Test
    public void should_match_when_has_any_role() {
        boolean match = ContextAuthority.builder().withRoles("角色1", "角色2")
                .build().match(ContextAuthority.builder()
                        .withRoles("角色2", "角色3")
                        .build());
        assertTrue(match);
    }

    @Test
    public void should_match_when_has_any_organization() {
        boolean match = ContextAuthority.builder().withOrganizations("部门1", "部门2")
                .build().match(ContextAuthority.builder()
                        .withOrganizations("部门2")
                        .build());
        assertTrue(match);
    }

    @Test
    public void should_not_match_when_has_no_organization() {
        boolean match = ContextAuthority.builder()
                .build().match(ContextAuthority.builder()
                        .withOrganizations("部门2")
                        .build());
        assertFalse(match);
    }
    @Test
    public void should_not_match_when_user_has_no_organization() {
        boolean match = ContextAuthority.builder()
                .withOrganizations("部门1")
                .build().match(ContextAuthority.builder()
                        .build());
        assertFalse(match);
    }
}