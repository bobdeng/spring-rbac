package cn.bobdeng.rbac.server.impl.function;

import cn.bobdeng.rbac.domain.function.Function;
import cn.bobdeng.rbac.domain.function.FunctionRepository;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

@Service
public class FunctionRepositoryImpl implements FunctionRepository {
    private final ExternalFunctionReader externalFunctionReader;

    public FunctionRepositoryImpl(ExternalFunctionReader externalFunctionReader) {
        this.externalFunctionReader = externalFunctionReader;
    }

    @SneakyThrows
    @Override
    public Stream<Function> list() {
        String content = Resources.toString(Resources.getResource("rbac/functions.json"), StandardCharsets.UTF_8);
        return Stream.concat(Stream.of(new Gson().fromJson(content, Function[].class)), externalFunctionReader.read().stream());
    }
}
