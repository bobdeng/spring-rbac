package cn.bobdeng.rbac.api.function;

import cn.bobdeng.rbac.domain.function.Function;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class FunctionVO {
    private String key;
    private String name;
    private List<FunctionVO> children;

    public FunctionVO(Function function) {
        this.key = function.getKey();
        this.name = function.description().getName();
        this.children = Optional.ofNullable(function.description().getChildren())
                .map(functions -> functions.stream().map(FunctionVO::new).collect(Collectors.toList()))
                .orElse(null);
    }
}
