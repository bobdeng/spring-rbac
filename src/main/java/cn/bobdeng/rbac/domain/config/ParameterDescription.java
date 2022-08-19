package cn.bobdeng.rbac.domain.config;

import cn.bobdeng.rbac.archtype.FieldChecker;
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

    public void validate() {
        FieldChecker.of("value", value)
                .lengthLessThan(100, "参数值应小于100个字符")
                .throwIfHasErrors();
    }
}
