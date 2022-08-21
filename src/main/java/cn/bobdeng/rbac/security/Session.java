package cn.bobdeng.rbac.security;

import cn.bobdeng.rbac.api.AdminToken;
import cn.bobdeng.rbac.api.UserToken;
import cn.bobdeng.rbac.domain.TenantRepository;
import lombok.ToString;

import java.util.Optional;

@ToString
public class Session {

    private UserToken userToken;
    private AdminToken adminToken;

    public Session(AdminToken adminToken) {

        this.adminToken = adminToken;
    }

    public Session(UserToken loginToken) {
        this.userToken = loginToken;
    }

    public boolean isAdmin() {
        return adminToken != null;
    }

    public UserToken userToken() {
        return this.userToken;
    }

    public Integer userId() {
        return userToken.getId();
    }

    public Session merge(Session session) {
        this.adminToken = Optional.ofNullable(session.adminToken).orElse(adminToken);
        this.userToken = Optional.ofNullable(session.userToken).orElse(userToken);
        return this;
    }

    public boolean hasPermission(String[] allows, TenantRepository tenantRepository) {
        if (isAdmin()) {
            return true;
        }
        return tenantRepository.findByIdentity(userToken.getTenant())
                .flatMap(tenant -> tenantRepository.rbacContext().asRbac(tenant).users().findByIdentity(userToken.getId()))
                .filter(user -> user.hasSomePermission(allows))
                .isPresent();
    }
}
