package cn.bobdeng.rbac.server.dao;

import cn.bobdeng.rbac.domain.Role;
import cn.bobdeng.rbac.domain.RoleDescription;
import cn.bobdeng.rbac.domain.Tenant;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_rbac_role")
public class RoleDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer tenantId;
    private String name;
    private String allows;

    public RoleDO(Role entity, Tenant tenant) {
        id = entity.identity();
        tenantId = tenant.identity();
        name = entity.description().getName();
        allows = String.join(",", entity.description().getAllows());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RoleDO roleDO = (RoleDO) o;
        return id != null && Objects.equals(id, roleDO.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Role toEntity() {
        return new Role(id, new RoleDescription(name, Arrays.asList(this.allows.split(","))));
    }
}
