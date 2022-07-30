package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.domain.Tenant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginForm {
    private String tenant;
    private String loginName;
    private String password;
    private String from;

    public LoginForm(Tenant tenant, String from) {
        this.tenant = tenant.description().getName();
        this.from = from;
    }
}
