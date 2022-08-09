package cn.bobdeng.rbac.security;

import cn.bobdeng.rbac.api.AdminToken;
import cn.bobdeng.rbac.api.UserToken;

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
}
