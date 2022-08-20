package cn.bobdeng.rbac.domain.rbac;

import cn.bobdeng.rbac.archtype.FieldChecker;
import cn.bobdeng.rbac.archtype.FieldIllegalException;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class RoleDescription {
    private String name;
    private List<String> allows;

    public RoleDescription(String name, List<String> allows) {
        this.name = name;
        this.allows = allows;
    }

    public void validate() {
        FieldChecker.of("allows", allows)
                .notEmpty("角色权限不能为空")
                .concat("name", name)
                .notEmpty("角色名不能为空")
                .lengthLessThan(20, "角色名不能大于20位")
                .throwIfHasErrors();
    }

    public boolean hasSomePermission(String[] allows) {
        return Stream.of(allows).anyMatch(this.allows::contains);
    }
}
