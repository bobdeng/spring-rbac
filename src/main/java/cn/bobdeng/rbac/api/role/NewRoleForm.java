package cn.bobdeng.rbac.api.role;

import lombok.Data;

import java.util.List;

@Data
public class NewRoleForm {
    private String name;
    private List<String> allows;
}
