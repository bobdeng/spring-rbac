package cn.bobdeng.rbac.domain.rbac;

import cn.bobdeng.rbac.archtype.Entity;
import lombok.Getter;

@Getter
public class Role implements Entity<Integer, RoleDescription> {
    private Integer id;
    private RoleDescription description;

    public Role(Integer id, RoleDescription description) {
        this.id = id;
        this.description = description;
    }

    public Role(RoleDescription description) {
        this.description = description;
    }

    @Override
    public Integer identity() {
        return id;
    }

    @Override
    public RoleDescription description() {
        return description;
    }

    public boolean hasSomePermission(String[] allows) {
        return description.hasSomePermission(allows);
    }
}
