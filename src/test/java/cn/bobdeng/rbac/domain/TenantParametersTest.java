package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.domain.config.ConfigurerImpl;
import cn.bobdeng.rbac.domain.config.Parameter;
import cn.bobdeng.rbac.domain.config.ParameterDescription;
import cn.bobdeng.rbac.domain.config.Parameters;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class TenantParametersTest {

    @Test
    void should_not_save_parameter_when_no_change() {
        Parameters parameters = mock(Parameters.class);
        ConfigurerImpl configurer = new ConfigurerImpl(parameters);
        when(parameters.list()).thenReturn(Stream.of(new Parameter("param.key1", new ParameterDescription("100", "param.key1"))));

        configurer.saveParameters(Arrays.asList(new Parameter("param.key1", new ParameterDescription("100", "param.key1"))));

        verify(parameters, never()).save(any(Parameter.class));
    }

}
