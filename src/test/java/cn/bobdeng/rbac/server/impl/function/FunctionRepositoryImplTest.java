package cn.bobdeng.rbac.server.impl.function;

import cn.bobdeng.rbac.domain.function.Function;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FunctionRepositoryImplTest {
    @Test
    public void should_read_resources(){
        ExternalFunctionReader external = mock(ExternalFunctionReader.class);
        when(external.read()).thenReturn(Collections.emptyList());
        FunctionRepositoryImpl functionRepository = new FunctionRepositoryImpl(external);
        List<Function> functions = functionRepository.list().toList();
    }
}