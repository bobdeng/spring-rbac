package cn.bobdeng.rbac.domain.function;

import cn.bobdeng.rbac.archtype.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Function implements Entity<String, FunctionDescription> {
    private String key;
    private FunctionDescription description;

    public Function(String key, FunctionDescription description) {
        this.key = key;
        this.description = description;
    }

    @Override
    public FunctionDescription description() {
        return description;
    }
}
