package cn.bobdeng.rbac.domain.config;

import cn.bobdeng.rbac.archtype.FieldIllegalException;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurerImplTest {
    @Test
    public void throw_when_value_is_too_long(){
        ConfigurerImpl configurer = new ConfigurerImpl(null, null);
        assertThrows(FieldIllegalException.class,()->
        configurer.saveParameters(Arrays.asList(
                new Parameter("param.key1",new ParameterDescription("","a0012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789123456789"))
        )));

    }

}