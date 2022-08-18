package cn.bobdeng.rbac.domain.rbac;

import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.domain.third.ThirdDescription;

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

        Tenant.Users users();

        Tenant.LoginNames loginNames();

        void newThirdIdentity(ThirdDescription thirdDescription);

        Tenant.ThirdIdentities thirdIdentities();
    }

    Rbac asRbac(Tenant tenant);
}
