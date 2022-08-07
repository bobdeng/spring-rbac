package cn.bobdeng.rbac.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@EqualsAndHashCode
@NoArgsConstructor
@Data
public class Tenant implements Entity<Integer, TenantDescription> {
    private Integer id;
    private TenantDescription description;
    private Users users;
    private LoginNames loginNames;
    private Roles roles;
    private HasMany<Integer, Domain> domains;

    public Tenant(TenantDescription tenantDescription) {

        this.description = tenantDescription;
    }

    public Tenant(Integer id, TenantDescription tenantDescription) {
        this.id = id;
        this.description = tenantDescription;
    }

    @Override
    public Integer identity() {
        return id;
    }

    @Override
    public TenantDescription description() {
        return description;
    }

    public User addUser(UserDescription userDescription) {
        return users.save(new User(userDescription));
    }

    public LoginName addLoginName(LoginNameDescription description) {
        if (loginNames.findByLoginName(description.getName()).isPresent()) {
            throw new DuplicateLoginNameException();
        }
        return loginNames.save(new LoginName(description));
    }

    public List<Domain> domains() {
        return domains.findAll().stream().collect(Collectors.toList());
    }

    public List<Role> roles() {
        return roles.list().collect(Collectors.toList());
    }

    public Role newRole(RoleDescription description) {
        description.validate();
        return roles.save(new Role(description));
    }

    public void saveRole(Role role) {
        role.description().validate();
        roles.save(role);
    }

    public void deleteRole(Integer roleId) {
        roles.findByIdentity(roleId).ifPresent(roles::delete);
    }

    public Optional<Role> getRole(Integer roleId) {
        return roles.findByIdentity(roleId);
    }


    public interface Users extends EntityList<Integer, User> {
        User save(User user);

        List<User> findByName(String name);

        Optional<User> findByAccount(String account);
    }

    public interface LoginNames extends EntityList<Integer, LoginName> {

        Optional<LoginName> findByLoginName(String name);
    }

    public interface Roles extends EntityList<Integer, Role> {

        void delete(Role role);
    }


}
