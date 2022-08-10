package cn.bobdeng.rbac.server.dao;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.tenant.organization.Organization;
import cn.bobdeng.rbac.domain.tenant.organization.OrganizationDescription;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrganizationDO that = (OrganizationDO) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Organization toEntity(TenantRepository tenantRepository) {
        Organization organization = new Organization(id, new OrganizationDescription(name, parentId));
        organization.setEmployees(tenantRepository.employees(organization));
        return organization;
    }
}
