package cn.bobdeng.rbac.server.dao;

import cn.bobdeng.rbac.archtype.LoginNameDescription;
import cn.bobdeng.rbac.domain.LoginName;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.User;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "t_rbac_login_name")
@Builder
@AllArgsConstructor
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

    public LoginName toEntity(Tenant.Users users) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LoginNameDO that = (LoginNameDO) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public LoginName toEntity(User user) {
        LoginName loginName = getLoginName(user);
        loginName.setUser(() -> user);
        return loginName;
    }
}
