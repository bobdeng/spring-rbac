package cn.bobdeng.rbac.api.user;

import lombok.Getter;

@Getter
public class ResetPasswordResult {
    private String password;

    public ResetPasswordResult(String password) {
        this.password = password;
    }
}
