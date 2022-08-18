package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.domain.config.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class TenantParametersTest {

    @Test
    void should_not_save_parameter_when_no_change() {
        Parameters parameters = mock(Parameters.class);
        ConfigurationContext context = mock(ConfigurationContext.class);
        Tenant tenant = new Tenant();
        when(context.parameters(tenant)).thenReturn(parameters);
        ConfigurerImpl configurer = new ConfigurerImpl(context, tenant);
        when(parameters.list()).thenReturn(Stream.of(new Parameter("param.key1", new ParameterDescription("100", "param.key1"))));

        configurer.saveParameters(Arrays.asList(new Parameter("param.key1", new ParameterDescription("100", "param.key1"))));

        verify(parameters, never()).save(any(Parameter.class));
    }

}
