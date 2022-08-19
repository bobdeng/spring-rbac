package cn.bobdeng.rbac.domain.rbac;

import cn.bobdeng.rbac.archtype.FieldChecker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public final class LoginNameDescription {
    @Getter
    private String name;
    @Getter
    private Integer userId;

    public void validate() {
        FieldChecker.of("name", name)
                .lengthBiggerThan(3, "登录名长度必须大于等于3")
                .lengthLessThan(20, "登录名长度必须小于等于20")
                .throwIfHasErrors();
    }
}
