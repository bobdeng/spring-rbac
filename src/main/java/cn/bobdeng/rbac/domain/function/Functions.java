package cn.bobdeng.rbac.domain.function;

import java.util.List;
import java.util.stream.Collectors;

public class Functions {
    private final FunctionRepository functionRepository;

    public Functions(FunctionRepository functionRepository) {
        this.functionRepository = functionRepository;
    }

    public List<Function> functions() {
        return functionRepository.list().collect(Collectors.toList());
    }
}
