package cn.bobdeng.rbac.api.user;

import lombok.Data;

import java.util.List;

@Data
public class NewUserForm {
    private String name;
    private String password;
    private String loginName;
    private List<Integer> roles;
}
