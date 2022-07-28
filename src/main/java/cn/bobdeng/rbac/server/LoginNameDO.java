package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.LoginName;
import cn.bobdeng.rbac.LoginNameDescription;
import cn.bobdeng.rbac.Tenant;
import cn.bobdeng.rbac.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_rbac_login_name")
@NoArgsConstructor
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
        LoginNameDescription description = (LoginNameDescription) entity.description();
        this.userId = description.getUser().identity();
        this.loginName = description.getName();
    }

    public LoginName toEntity() {
        User user = new User(userId, null);
        LoginNameDescription description = new LoginNameDescription(loginName, user);
        return new LoginName(id, description);
    }
}
