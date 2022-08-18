package cn.bobdeng.rbac.server.dao;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.organization.OrganizationContext;
import cn.bobdeng.rbac.domain.organization.Organization;
import cn.bobdeng.rbac.domain.organization.OrganizationDescription;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "t_rbac_organization")
public class OrganizationDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer tenantId;
    private String name;
    private Integer parentId;

    public OrganizationDO(Organization entity, Tenant tenant) {
        this.id = entity.identity();
        this.tenantId = tenant.identity();
        this.name = entity.description().getName();
        this.parentId = entity.description().getParent();
    }

    public Organization toEntity(Tenant tenant, OrganizationContext organizationContext) {
        Organization organization = new Organization(id, new OrganizationDescription(name, parentId));
        organization.setTenant(() -> tenant);
        organization.setOrganizationContext(organizationContext);
        return organization;
    }
}
