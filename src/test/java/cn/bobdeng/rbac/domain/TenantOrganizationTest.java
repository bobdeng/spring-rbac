package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.domain.tenant.organization.OrganizationDescription;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TenantOrganizationTest {
    @Test
    public void should_throw_when_organization_name_empty() {
        assertThrows(FieldIllegalException.class, () ->
                new Tenant().newOrganization(new OrganizationDescription("", null)));
    }

}