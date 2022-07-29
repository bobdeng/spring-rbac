package cn.bobdeng.rbac.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@NoArgsConstructor
public class User implements Entity<Integer, UserDescription> {
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private UserDescription description;
    @Setter
    @Getter
    private UserPassword userPassword;

    public User(Integer id, UserDescription description) {
        this.id = id;
        this.description = description;
    }

    public User(UserDescription userDescription) {
        this(null, userDescription);
    }

    @Override
    public Integer identity() {
        return id;
    }

    @Override
    public UserDescription description() {
        return description;
    }

    public void savePassword(RawPassword rawPassword) {
        Password password = new Password(this.identity(), new PasswordDescription(rawPassword, userPassword));
        userPassword.save(password);
    }

    public boolean verifyPassword(String rawPassword) {
        return userPassword.findByIdentity(identity())
                .map(password -> userPassword.match(rawPassword, password.description().getPassword()))
                .orElse(false);
    }

    public interface UserPassword extends EntityList<Integer, Password> {
        String encodePassword(String rawPassword);

        boolean match(String rawPassword, String password);
    }
}
