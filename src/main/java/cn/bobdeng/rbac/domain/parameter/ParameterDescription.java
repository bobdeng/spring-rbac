package cn.bobdeng.rbac.domain.parameter;

import lombok.Getter;

@Getter
public class ParameterDescription {
    private String name;
    private String value;
    private String key;

    public ParameterDescription(String name, String value, String key) {
        this.name = name;
        this.value = value;
        this.key = key;
    }

    public ParameterDescription(String value, String key) {
        this.value = value;
        this.key = key;
    }
}
