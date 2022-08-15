package cn.bobdeng.rbac.server.impl.function;

import cn.bobdeng.rbac.domain.function.Function;
import cn.bobdeng.rbac.domain.function.FunctionRepository;
import cn.bobdeng.rbac.utils.ResourceReader;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

@Service
public class FunctionRepositoryImpl implements FunctionRepository {
    private final ExternalFunctionReader externalFunctionReader;
    private final ResourceReader resourceReader;

    public FunctionRepositoryImpl(ExternalFunctionReader externalFunctionReader, ResourceReader resourceReader) {
        this.externalFunctionReader = externalFunctionReader;
        this.resourceReader = resourceReader;
    }

    @Override
    public Stream<Function> list() {
        try {
            String content = resourceReader.read("rbac/functions.json");
            return Stream.concat(Stream.of(new Gson().fromJson(content, Function[].class)), externalFunctionReader.read().stream());
        } catch (Exception e) {
            return Stream.empty();
        }
    }
}
