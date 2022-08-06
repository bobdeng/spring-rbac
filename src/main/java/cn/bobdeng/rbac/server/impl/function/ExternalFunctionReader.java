package cn.bobdeng.rbac.server.impl.function;

import cn.bobdeng.rbac.domain.function.Function;

import java.util.List;

public interface ExternalFunctionReader {
    List<Function> read();
}
