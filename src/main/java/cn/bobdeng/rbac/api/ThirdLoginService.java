package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.domain.UserDescription;
import cn.bobdeng.rbac.domain.third.ThirdDescription;
import cn.bobdeng.rbac.domain.third.ThirdIdentity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThirdLoginService {
    public UserToken login(Tenant tenant, ThirdLoginForm thirdLoginForm) {
        Optional<ThirdIdentity> thirdIdentity = tenant.thirdIdentities().findByNameAndIdentity(thirdLoginForm.getThirdName(), thirdLoginForm.getIdentity());
        if (thirdIdentity.isEmpty()) {
            User user = tenant.addUser(new UserDescription(thirdLoginForm.getUserName()));
            ThirdDescription thirdDescription = new ThirdDescription(thirdLoginForm.getIdentity(), thirdLoginForm.getThirdName(), user.identity());
            tenant.newThirdIdentity(thirdDescription);
            return new UserToken(user, tenant);
        }
        Integer userId = thirdIdentity.get().description().getUserId();
        User user = tenant.users().findByIdentity(userId).orElseThrow(ObjectNotFoundException::new);
        return new UserToken(user, tenant);
    }
}
