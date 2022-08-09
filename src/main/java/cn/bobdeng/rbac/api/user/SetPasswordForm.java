package cn.bobdeng.rbac.api.user;

import lombok.Data;

@Data
public class SetPasswordForm {
    private String password;
    private String newPassword;
}
