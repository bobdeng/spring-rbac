package cn.bobdeng.rbac.domain.function;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FunctionDescription {
    private String name;
    private List<Function> children;

    public FunctionDescription(String name, List<Function> children) {
        this.name = name;
        this.children = children;
    }
}
