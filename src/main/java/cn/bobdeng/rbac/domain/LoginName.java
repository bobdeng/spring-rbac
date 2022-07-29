package cn.bobdeng.rbac.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode
public class LoginName implements Entity<Integer, LoginNameDescription> {
    private Integer id;
    private LoginNameDescription description;

    public LoginName(Integer id, LoginNameDescription description) {
        this.id = id;
        this.description = description;
    }

    public LoginName(LoginNameDescription description) {
        this(null, description);
    }

    @Override
    public Integer identity() {
        return id;
    }

    @Override
    public LoginNameDescription description() {
        return description;
    }
}
