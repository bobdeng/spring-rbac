package cn.bobdeng.rbac.domain.function;

import lombok.Data;

import java.util.List;

@Data
public class FunctionDescription {
    private String name;
    private List<Function> children;
}
