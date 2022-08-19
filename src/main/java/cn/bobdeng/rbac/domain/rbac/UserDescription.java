package cn.bobdeng.rbac.domain.rbac;

import cn.bobdeng.rbac.archtype.FieldChecker;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class UserDescription {
    private String name;
    private User.UserStatus status;

    public UserDescription(String name) {

        this.name = name;
        this.status = User.UserStatus.Normal;
    }

    public UserDescription(String name, User.UserStatus status) {
        this.name = name;
        this.status = status;
    }

    public void validate() {
        FieldChecker.of("name", name)
                .lengthLessThan(20, "姓名长度应小于等于20个字符")
                .notEmpty("姓名不能为空")
                .throwIfHasErrors();
    }
}
