package cn.bobdeng.rbac.domain.tenant.organization;

import cn.bobdeng.rbac.domain.FieldChecker;
import lombok.Data;

@Data
public class OrganizationDescription {
    private String name;
    private Integer parent;

    public OrganizationDescription(String name, Integer parent) {
        this.name = name;
        this.parent = parent;
    }

    public void validate() {
        FieldChecker.of("name", this.name)
                .notEmpty("名称不能为空")
                .throwIfHasErrors();
    }
}
