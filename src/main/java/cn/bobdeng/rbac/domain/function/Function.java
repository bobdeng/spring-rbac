package cn.bobdeng.rbac.domain.function;

import cn.bobdeng.rbac.domain.Entity;
import lombok.Data;

@Data
public class Function implements Entity<String, FunctionDescription> {
    private String key;
    private FunctionDescription description;

    @Override
    public String identity() {
        return key;
    }

    @Override
    public FunctionDescription description() {
        return description;
    }
}
