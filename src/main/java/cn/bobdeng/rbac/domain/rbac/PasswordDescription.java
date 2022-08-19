package cn.bobdeng.rbac.domain.rbac;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
public class PasswordDescription {
    private String password;

    public PasswordDescription(String password) {
        this.password = password;
    }

    public PasswordDescription(RawPassword rawPassword, User.UserPassword userPassword) {
        this.password = userPassword.encodePassword(rawPassword.getRawPassword());
    }
}
