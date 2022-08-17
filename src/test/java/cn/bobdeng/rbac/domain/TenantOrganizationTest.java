package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.domain.organization.OrganizationStructureImpl;
import cn.bobdeng.rbac.domain.tenant.organization.OrganizationDescription;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TenantOrganizationTest {
    @Test
    public void should_throw_when_organization_name_empty() {
        assertThrows(FieldIllegalException.class, () ->
                new OrganizationStructureImpl(null).add(new OrganizationDescription("", null)));
    }

}