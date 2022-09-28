package cn.bobdeng.rbac.api.user;

import lombok.Data;

import java.util.List;

@Data
public class SetRoleForm {
    private List<Integer> roles;
}
