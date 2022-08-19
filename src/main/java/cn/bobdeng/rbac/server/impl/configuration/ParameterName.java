package cn.bobdeng.rbac.server.impl.configuration;

import lombok.Getter;

@Getter
public class ParameterName {
    private String key;
    private String name;
    private String defaultValue;

    public ParameterName(String key, String name, String defaultValue) {
        this.key = key;
        this.name = name;
        this.defaultValue = defaultValue;
    }
}
