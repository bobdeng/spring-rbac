package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.archtype.Entity;
import cn.bobdeng.rbac.archtype.HasMany;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
@NoArgsConstructor
public class Tenant implements Entity<Integer, TenantDescription> {
    @Getter
    @Setter
    private Integer id;
    @Setter
    @Getter
    private TenantDescription description;
    @Setter
    private HasMany<Integer, Domain> domains;

    public Tenant(TenantDescription tenantDescription) {

        this.description = tenantDescription;
    }

    public Tenant(Integer id, TenantDescription tenantDescription) {
        this.id = id;
        this.description = tenantDescription;
    }

    @Override
    public Integer identity() {
        return id;
    }

    public List<Domain> domains() {
        return domains.findAll().stream().collect(Collectors.toList());
    }


}
