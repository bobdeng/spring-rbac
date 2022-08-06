package cn.bobdeng.rbac.domain;

import lombok.Data;

import java.util.List;
@Data
public class RoleDescription {
    private String name;
    private List<String> allows;

    public RoleDescription(String name, List<String> allows) {
        this.name = name;
        this.allows = allows;
    }
}
