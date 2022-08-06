package cn.bobdeng.rbac.server.impl.function;

import cn.bobdeng.rbac.domain.function.Function;
import cn.bobdeng.rbac.domain.function.FunctionRepository;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FunctionRepositoryImpl implements FunctionRepository {
    @Override
    public List<Function> subList(int from, int to) {
        return null;
    }

    @SneakyThrows
    @Override
    public Stream<Function> list() {
        String content = Resources.toString(Resources.getResource("rbac/functions.json"), StandardCharsets.UTF_8);
        return Stream.of(new Gson().fromJson(content, Function[].class));
    }

    @Override
    public Optional<Function> findByIdentity(String s) {
        return Optional.empty();
    }

    @Override
    public Function save(Function entity) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
