package cn.bobdeng.rbac.api.function;

import cn.bobdeng.rbac.domain.function.FunctionRepository;
import cn.bobdeng.rbac.domain.function.Functions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FunctionController {
    private final FunctionRepository functionRepository;

    public FunctionController(FunctionRepository functionRepository) {
        this.functionRepository = functionRepository;
    }

    @GetMapping("/api/1.0/functions")
    public List<FunctionVO> listFunctions() {
        return new Functions(functionRepository).functions()
                .stream().map(FunctionVO::new).collect(Collectors.toList());
    }
}
