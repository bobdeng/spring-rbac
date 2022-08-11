package cn.bobdeng.rbac.api.user;

import lombok.Data;

@Data
public class NewLoginNameForm {
    private Integer userId;
    private String loginName;
}
