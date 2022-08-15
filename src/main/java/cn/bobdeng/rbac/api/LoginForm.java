package cn.bobdeng.rbac.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginForm {
    private String loginName;
    private String password;
}
