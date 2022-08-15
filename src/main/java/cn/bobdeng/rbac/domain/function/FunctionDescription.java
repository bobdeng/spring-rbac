package cn.bobdeng.rbac.domain.function;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FunctionDescription {
    private String name;
    private List<Function> children;

    public FunctionDescription(String name, List<Function> children) {
        this.name = name;
        this.children = children;
    }
}
