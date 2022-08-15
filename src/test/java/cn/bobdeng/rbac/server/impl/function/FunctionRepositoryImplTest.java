package cn.bobdeng.rbac.server.impl.function;

import cn.bobdeng.rbac.domain.function.Function;
import cn.bobdeng.rbac.utils.ResourceReader;
import cn.bobdeng.rbac.utils.ResourceReaderImpl;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FunctionRepositoryImplTest {
    @Test
    public void should_read_resources() throws IOException {
        ExternalFunctionReader external = mock(ExternalFunctionReader.class);
        when(external.read()).thenReturn(Collections.emptyList());
        ResourceReader resourceReader = mock(ResourceReader.class);
        when(resourceReader.read("rbac/functions.json")).thenReturn("[]");
        FunctionRepositoryImpl functionRepository = new FunctionRepositoryImpl(external, resourceReader);
        List<Function> functions = functionRepository.list().toList();
        assertEquals(0, functions.size());
    }

    @Test
    public void should_read_external() throws IOException {
        ExternalFunctionReader external = mock(ExternalFunctionReader.class);
        when(external.read()).thenReturn(Arrays.asList(new Function()));
        ResourceReader resourceReader = mock(ResourceReader.class);
        when(resourceReader.read("rbac/functions.json")).thenReturn("[]");
        FunctionRepositoryImpl functionRepository = new FunctionRepositoryImpl(external, resourceReader);
        List<Function> functions = functionRepository.list().toList();
        assertEquals(1, functions.size());
    }

    @Test
    public void should_return_empty_when_read_error() throws IOException {
        ExternalFunctionReader external = mock(ExternalFunctionReader.class);
        when(external.read()).thenReturn(List.of());
        ResourceReader resourceReader = mock(ResourceReader.class);
        when(resourceReader.read("rbac/functions.json")).thenThrow(new IOException());
        FunctionRepositoryImpl functionRepository = new FunctionRepositoryImpl(external, resourceReader);
        List<Function> functions = functionRepository.list().toList();
        assertEquals(0, functions.size());
    }

    @Test
    public void read_real() {
        ExternalFunctionReader external = mock(ExternalFunctionReader.class);
        when(external.read()).thenReturn(List.of());
        ResourceReader resourceReader = new ResourceReaderImpl();
        FunctionRepositoryImpl functionRepository = new FunctionRepositoryImpl(external, resourceReader);
        List<Function> functions = functionRepository.list().toList();
        assertNotEquals(0, functions.size());
    }
}