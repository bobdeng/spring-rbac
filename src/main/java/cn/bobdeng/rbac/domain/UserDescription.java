package cn.bobdeng.rbac.domain;

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
}
