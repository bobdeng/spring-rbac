package cn.bobdeng.rbac.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class PasswordDescription {
    private String password;

    public PasswordDescription(String password) {
        this.password = password;
    }

    public PasswordDescription(RawPassword rawPassword, User.UserPassword userPassword) {
        this.password = userPassword.encodePassword(rawPassword.getRawPassword());
    }
}
