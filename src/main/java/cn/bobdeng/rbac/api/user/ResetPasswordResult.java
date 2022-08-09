package cn.bobdeng.rbac.api.user;

import lombok.Data;

@Data
public class ResetPasswordResult {
    private String password;

    public ResetPasswordResult(String password) {
        this.password = password;
    }
}
