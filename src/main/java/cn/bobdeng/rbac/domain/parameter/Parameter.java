package cn.bobdeng.rbac.domain.parameter;

import cn.bobdeng.rbac.archtype.Entity;
import lombok.Getter;

import java.util.Map;

public class Parameter implements Entity<Integer, ParameterDescription> {
    @Getter
    private Integer id;
    @Getter
    private ParameterDescription description;

    public Parameter(Integer id, ParameterDescription description) {
        this.id = id;
        this.description = description;
    }

    public Parameter(ParameterDescription description) {
        this.description = description;
    }

    @Override
    public Integer identity() {
        return id;
    }

    @Override
    public ParameterDescription description() {
        return description;
    }

    public boolean isChanged(Map<String, String> values) {
        return !description.getValue().equals(values.get(description.getKey()));
    }
}
