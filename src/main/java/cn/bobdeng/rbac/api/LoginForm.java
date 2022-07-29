package cn.bobdeng.rbac.api;

import lombok.Data;

@Data
public class LoginForm {
    private String tenant;
    private String loginName;
    private String password;
}
