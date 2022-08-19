package cn.bobdeng.rbac.domain.organization;

import cn.bobdeng.rbac.archtype.FieldChecker;
import lombok.Getter;

@Getter
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
                .lengthLessThan(20,"组织名称必须小于等于20个字")
                .throwIfHasErrors();
    }
}
