package cn.bobdeng.rbac.api.parameter;

import cn.bobdeng.rbac.server.impl.configuration.ExternalParameters;
import cn.bobdeng.rbac.server.impl.configuration.ParameterName;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Primary
public class ExternalParametersTest implements ExternalParameters {
    @Override
    public List<ParameterName> parameters() {
        return Arrays.asList(
                new ParameterName("param.key1", "系统参数1", "102")
        );
    }
}
