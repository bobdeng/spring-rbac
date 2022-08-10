package cn.bobdeng.rbac.domain;

import lombok.*;

@EqualsAndHashCode
@NoArgsConstructor
public class Domain implements Entity<Integer, DomainDescription> {
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private DomainDescription description;
    @Setter
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
