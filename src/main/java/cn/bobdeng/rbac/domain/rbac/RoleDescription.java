package cn.bobdeng.rbac.domain.rbac;

import cn.bobdeng.rbac.domain.FieldChecker;
import cn.bobdeng.rbac.domain.FieldIllegalException;
import lombok.Data;
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
        List<FieldChecker.FieldError> errors =
                Stream.of(validateAllows(), validateName())
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
        if (errors.size() > 0) {
            throw new FieldIllegalException(errors);
        }

    }

    private List<FieldChecker.FieldError> validateAllows() {
        return FieldChecker.of("allows", allows)
                .notEmpty("角色权限不能为空")
                .getErrors();
    }

    private List<FieldChecker.FieldError> validateName() {
        return FieldChecker.of("name", name)
                .notEmpty("角色名不能为空")
                .lengthLessThan(20, "角色名不能大于20位")
                .getErrors();
    }

    public boolean hasSomePermission(String[] allows) {
        return Stream.of(allows).anyMatch(this.allows::contains);
    }
}
