package cn.bobdeng.rbac.domain.rbac;

import cn.bobdeng.rbac.archtype.EntityList;
import cn.bobdeng.rbac.domain.*;

import java.util.List;
import java.util.Optional;

public interface RbacContext {
    interface Rbac {

        User addUser(UserDescription userDescription);

        LoginName addLoginName(LoginNameDescription description);

        List<Role> listRoles();

        Role newRole(RoleDescription description);

        void saveRole(Role role);

        void deleteRole(Integer roleId);

        Optional<Role> getRole(Integer roleId);

        void newThirdIdentity(ThirdDescription thirdDescription);

        Roles roles();

        LoginNames loginNames();

        Users users();

        ThirdIdentities thirdIdentities();
    }

    User.UserRoles userRoles(User user);

    RbacContext.Roles roles(Tenant tenant);

    RbacContext.Users users(Tenant tenant);

    RbacContext.LoginNames loginNames(Tenant tenant);

    User.UserLock userLock(User user);

    ThirdIdentities thirdIdentities(Tenant tenant);

    User.UserPassword userPassword(User user);

    Rbac asRbac(Tenant tenant);

    interface Roles extends EntityList<Integer, Role> {

        void delete(Role role);
    }

    interface ThirdIdentities extends EntityList<Integer, ThirdIdentity> {

        Optional<ThirdIdentity> findByNameAndIdentity(String thirdName, String identity);
    }

    interface LoginNames extends EntityList<Integer, LoginName> {

        Optional<LoginName> findByLoginName(String name);

        void delete(Integer id);

        Optional<LoginName> findByUser(Integer id);
    }

    interface Users extends EntityList<Integer, User> {
        User save(User user);

        List<User> findByName(String name);
    }
}
