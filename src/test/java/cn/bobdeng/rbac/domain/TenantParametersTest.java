package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.domain.parameter.Parameter;
import cn.bobdeng.rbac.domain.parameter.ParameterDescription;
import cn.bobdeng.rbac.domain.parameter.Parameters;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class TenantParametersTest {

    @Test
    void should_not_save_parameter_when_no_change() {
        Tenant tenant = new Tenant();
        Parameters parameters = mock(Parameters.class);
        when(parameters.list()).thenReturn(Stream.of(new Parameter(1, new ParameterDescription("100", "param.key1"))));
        tenant.setParameters(parameters);

        tenant.saveParameters(Arrays.asList(new ParameterDescription("100", "param.key1")));

        verify(parameters,never()).save(any(Parameter.class));
    }

}
