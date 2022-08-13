package cn.bobdeng.rbac.api;

import lombok.Data;

@Data
public class ThirdLoginForm {
    private String thirdName;
    private String identity;
    private String userName;
}
