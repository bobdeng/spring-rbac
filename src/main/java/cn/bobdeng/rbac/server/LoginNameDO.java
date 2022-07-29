package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.domain.LoginName;
import cn.bobdeng.rbac.domain.LoginNameDescription;
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
        this.userId = entity.description().getUser().identity();
        this.loginName = entity.description().getName();
    }

    public LoginName toEntity() {
        User user = new User(userId, null);
        LoginNameDescription description = new LoginNameDescription(loginName, user);
        return new LoginName(id, description);
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
}
