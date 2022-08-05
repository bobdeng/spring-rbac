package cn.bobdeng.rbac.security;

import cn.bobdeng.rbac.api.AdminToken;

public class Session {

    private AdminToken adminToken;

    public Session(AdminToken adminToken) {

        this.adminToken = adminToken;
    }
}
