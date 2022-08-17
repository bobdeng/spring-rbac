package cn.bobdeng.rbac.domain.parameter;

import lombok.Getter;

public class ParameterDescription {
    @Getter
    private String name;
    @Getter
    private String value;

    public ParameterDescription(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
