package cn.bobdeng.rbac.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginForm {
    private String tenant;
    private String loginName;
    private String password;
    private String from;

}
