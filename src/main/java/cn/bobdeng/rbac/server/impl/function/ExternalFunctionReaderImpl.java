package cn.bobdeng.rbac.server.impl.function;

import cn.bobdeng.rbac.domain.function.Function;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ExternalFunctionReaderImpl implements ExternalFunctionReader {
    @Override
    public List<Function> read() {
        return Collections.emptyList();
    }
}
