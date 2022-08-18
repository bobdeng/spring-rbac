package cn.bobdeng.rbac.server.dao;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.domain.UserDescription;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.server.impl.TenantUsers;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public final class UserDO {
    private Integer id;
    private String name;
    private Integer tenantId;
    private String status;


    public UserDO(User user, Tenant tenant) {
        this.id = user.identity();
        this.name = user.description().getName();
        this.tenantId = tenant.identity();
        this.status = user.description().getStatus().getStatus();
    }


    public User toUser(TenantRepository tenantRepository, RbacContext rbacContext) {
        User user = new User(id, new UserDescription(name, User.UserStatus.of(status)));
        user.setRbacContext(rbacContext);
        user.setTenant(() -> tenantRepository.findByIdentity(tenantId).orElse(null));
        return user;
    }

}
