package cn.bobdeng.rbac.domain.config;

import cn.bobdeng.rbac.archtype.Entity;
import lombok.Getter;

import java.util.Map;

public class Parameter implements Entity<String, ParameterDescription> {
    @Getter
    private String id;
    @Getter
    private ParameterDescription description;

    public Parameter(String id, ParameterDescription description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public String identity() {
        return id;
    }

    @Override
    public ParameterDescription description() {
        return description;
    }

    public boolean isChanged(Map<String, String> values) {
        return !description.getValue().equals(values.get(id));
    }
}
