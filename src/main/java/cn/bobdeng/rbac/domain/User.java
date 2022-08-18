package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.archtype.Entity;
import cn.bobdeng.rbac.archtype.EntityList;
import cn.bobdeng.rbac.archtype.HasOne;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Stream;

@EqualsAndHashCode
public class User implements Entity<Integer, UserDescription> {
    @Getter
    private Integer id;
    @Getter
    private UserDescription description;
    @Setter
    private HasOne<Tenant> tenant;
    @Setter
    private RbacContext rbacContext;


    public User(Integer id, UserDescription description) {
        this.id = id;
        this.description = description;
    }

    public UserRoles userRoles() {
        return rbacContext.userRoles(this);
    }

    public Tenant tenant() {
        return tenant.get();
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
        Password password = new Password(this.identity(), new PasswordDescription(rawPassword, getUserPassword()));
        getUserPassword().save(password);
    }

    private UserPassword getUserPassword() {
        return rbacContext.userPassword(this);
    }

    public boolean verifyPassword(String rawPassword) {
        return getUserPassword().findByIdentity(identity())
                .map(password -> getUserPassword().match(rawPassword, password.description().getPassword()))
                .orElse(false);
    }

    public boolean hasSomePermission(String[] allows) {
        return userRoles().list().anyMatch(role -> role.hasSomePermission(allows));
    }

    public void setRoles(List<Role> roles) {
        roles.forEach(role -> userRoles().save(role));
    }

    public void lock() {
        this.description = new UserDescription(description.getName(), UserStatus.Locked);
        getUsers().save(this);
    }

    private RbacContext.Users getUsers() {
        return rbacContext.users(tenant.get());
    }

    public void unlock() {
        this.description = new UserDescription(description.getName(), UserStatus.Normal);
        getUsers().save(this);
    }

    public boolean normal() {
        return description.getStatus() == UserStatus.Normal;
    }

    public interface UserPassword extends EntityList<Integer, Password> {
        String encodePassword(String rawPassword);

        boolean match(String rawPassword, String password);
    }

    public interface UserRoles extends EntityList<Integer, Role> {

    }

    public enum UserStatus {
        Normal("normal"), Locked("locked");
        @Getter
        private String status;

        UserStatus(String status) {

            this.status = status;
        }

        public static UserStatus of(String value) {
            return Stream.of(UserStatus.values())
                    .filter(status -> status.status.equals(value))
                    .findFirst().orElse(Normal);
        }
    }
}
