package cn.bobdeng.rbac.server.dao;

import cn.bobdeng.rbac.domain.LoginNameDescription;
import cn.bobdeng.rbac.domain.LoginName;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import lombok.*;

import javax.persistence.*;

@RequiredArgsConstructor
@Entity
@Table(name = "t_rbac_login_name")
public class LoginNameDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer tenantId;
    private String loginName;
    private Integer userId;

    public LoginNameDO(LoginName entity, Tenant tenant) {
        this.id = entity.identity();
        this.tenantId = tenant.identity();
        this.userId = entity.description().getUserId();
        this.loginName = entity.description().getName();
    }

    public LoginName toEntity(RbacContext.Users users) {
        User user = new User(userId, null);
        LoginName loginName = getLoginName(user);
        loginName.setUser(() -> users.findByIdentity(userId).orElse(null));
        return loginName;
    }

    private LoginName getLoginName(User user) {
        LoginNameDescription description = new LoginNameDescription(loginName, user.getId());
        LoginName loginName = new LoginName(id, description);
        return loginName;
    }
}
