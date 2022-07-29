package cn.bobdeng.rbac.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@NoArgsConstructor
@EqualsAndHashCode
public class LoginName implements Entity<Integer, LoginNameDescription> {
    private Integer id;
    private LoginNameDescription description;
    @Setter
    private HasOne<User> user;

    public LoginName(Integer id, LoginNameDescription description) {
        this.id = id;
        this.description = description;
    }

    public Optional<User> user() {
        return user.get();
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
