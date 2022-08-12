package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.archtype.Entity;
import cn.bobdeng.rbac.archtype.EntityList;
import cn.bobdeng.rbac.archtype.HasMany;
import cn.bobdeng.rbac.archtype.LoginNameDescription;
import cn.bobdeng.rbac.domain.tenant.organization.Organization;
import cn.bobdeng.rbac.domain.tenant.organization.OrganizationDescription;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@EqualsAndHashCode
@NoArgsConstructor
public class Tenant implements Entity<Integer, TenantDescription> {
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private TenantDescription description;
    @Setter
    private Users users;
    @Setter
    private LoginNames loginNames;
    @Setter
    private Roles roles;
    @Setter
    private HasMany<Integer, Domain> domains;
    @Setter
    private Organizations organizations;

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

    public Users users() {
        return users;
    }

    public LoginNames loginNames() {
        return loginNames;
    }

    public Organizations organizations() {
        return organizations;
    }

    public void newOrganization(OrganizationDescription description) {
        description.validate();
        organizations.save(new Organization(description));
    }


    public interface Users extends EntityList<Integer, User> {
        User save(User user);

        List<User> findByName(String name);
    }

    public interface LoginNames extends EntityList<Integer, LoginName> {

        Optional<LoginName> findByLoginName(String name);

        void delete(Integer id);

        Optional<LoginName> findByUser(Integer id);
    }

    public interface Roles extends EntityList<Integer, Role> {

        void delete(Role role);
    }

    public interface Organizations extends EntityList<Integer, Organization> {

    }


}
