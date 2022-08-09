package cn.bobdeng.rbac.domain.tenant.organization;

import lombok.Data;

@Data
public class OrganizationDescription {
    private String name;
    private Integer parent;

    public OrganizationDescription(String name, Integer parent) {
        this.name = name;
        this.parent = parent;
    }
}
