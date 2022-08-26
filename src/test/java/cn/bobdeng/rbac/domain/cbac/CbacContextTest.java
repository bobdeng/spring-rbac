package cn.bobdeng.rbac.domain.cbac;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CbacContextTest {
    private CbacContext.Contexts contexts;
    private CbacImpl cbac;

    @BeforeEach
    public void setup() {
        contexts = mock(CbacContext.Contexts.class);
        cbac = new CbacImpl();
        cbac.setContexts(contexts);
    }

    @Test
    public void should_save_cbac_context() {
        Context context = getContext();

        cbac.newContext(context);

        verify(contexts).save(context);
    }

    @Test
    public void should_throw_when_no_any_authority() {
        EmptyAuthorityException e = assertThrows(EmptyAuthorityException.class, () ->
                cbac.newContext(new Context(new ContextDescription(new ContextObject("type", "1"), ContextAuthority.builder().build()))));
        assertEquals("权限不能为空", e.getMessage());
    }

    @Test
    public void should_throw_when_no_any_object() {
        EmptyObjectException e = assertThrows(EmptyObjectException.class, () ->
                cbac.newContext(new Context(new ContextDescription(new ContextObject(null, null), ContextAuthority.builder().build()))));
        assertEquals("对象不能为空", e.getMessage());
    }

    @NotNull
    private Context getContext() {
        ContextObject contextObject = new ContextObject("mission", "1");
        ContextAuthority contextAuthority = ContextAuthority.builder()
                .withUser(1)
                .withRoles("角色1", "角色2")
                .withOrganizations("组织1")
                .build();
        ContextDescription contextDescription = new ContextDescription(contextObject, contextAuthority);
        Context context = new Context(contextDescription);
        return context;
    }

    @Test
    public void should_has_permission_when_user_id_match() {
        when(contexts.findByObject(new ContextObject("mission", "1"))).thenReturn(
                Stream.of(getContext())
        );

        boolean allowed = cbac.isAllowed(new ContextObject("mission", "1"), ContextAuthority.builder().withUser(1).build());
        assertTrue(allowed);
    }
}