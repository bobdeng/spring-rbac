package cn.bobdeng.rbac.domain.rbac;

import cn.bobdeng.rbac.archtype.EntityList;
import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.domain.third.ThirdDescription;
import cn.bobdeng.rbac.domain.third.ThirdIdentity;

import java.util.List;
import java.util.Optional;

public interface RbacContext {
    interface Rbac {

        User addUser(UserDescription userDescription);

        LoginName addLoginName(LoginNameDescription description);

        List<Role> roles();

        Role newRole(RoleDescription description);

        void saveRole(Role role);

        void deleteRole(Integer roleId);

        Optional<Role> getRole(Integer roleId);

        Users users();

        LoginNames loginNames();

        void newThirdIdentity(ThirdDescription thirdDescription);

        ThirdIdentities thirdIdentities();
    }

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
