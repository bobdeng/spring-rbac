package cn.bobdeng.rbac.api.role;

import cn.bobdeng.rbac.domain.RoleDescription;
import lombok.Data;

import java.util.List;

@Data
public class NewRoleForm {
    private String name;
    private List<String> allows;

    RoleDescription getDescription() {
        return new RoleDescription(getName(), getAllows());
    }
}
