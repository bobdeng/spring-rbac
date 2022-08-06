package cn.bobdeng.rbac.domain.function;

import cn.bobdeng.rbac.domain.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Function implements Entity<String, FunctionDescription> {
    private String key;
    private FunctionDescription description;

    public Function(String key, FunctionDescription description) {
        this.key = key;
        this.description = description;
    }

    @Override
    public String identity() {
        return key;
    }

    @Override
    public FunctionDescription description() {
        return description;
    }
}
