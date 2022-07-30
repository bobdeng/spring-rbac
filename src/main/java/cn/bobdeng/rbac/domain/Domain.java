package cn.bobdeng.rbac.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Domain implements Entity<Integer, DomainDescription> {
    private Integer id;
    private DomainDescription description;
    private HasOne<Tenant> tenant;

    public Domain(Integer id, DomainDescription description) {
        this.id = id;
        this.description = description;
    }

    public Domain(DomainDescription description) {
        this(null, description);
    }

    @Override
    public Integer identity() {
        return id;
    }

    @Override
    public DomainDescription description() {
        return description;
    }

    public Tenant tenant() {
        return tenant.get();
    }
}
