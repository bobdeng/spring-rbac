package cn.bobdeng.rbac.server.impl.function;

import cn.bobdeng.rbac.domain.function.Function;
import cn.bobdeng.rbac.domain.function.FunctionDescription;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ExternalFunctionReaderImpl implements ExternalFunctionReader {
    @Override
    public List<Function> read() {
        return Arrays.asList(
                new Function("out1", new FunctionDescription("外部功能", null))
        );
    }
}
