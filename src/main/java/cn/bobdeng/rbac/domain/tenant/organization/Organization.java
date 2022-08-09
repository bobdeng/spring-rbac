package cn.bobdeng.rbac.domain.tenant.organization;

import cn.bobdeng.rbac.domain.Entity;
import lombok.Getter;

public class Organization implements Entity<Integer, OrganizationDescription> {
    @Getter
    private Integer id;
    @Getter
    private OrganizationDescription description;

    public Organization(Integer id, OrganizationDescription description) {
        this.id = id;
        this.description = description;
    }

    public Organization(OrganizationDescription description) {
        this.description = description;
    }

    @Override
    public Integer identity() {
        return id;
    }

    @Override
    public OrganizationDescription description() {
        return description;
    }
}
