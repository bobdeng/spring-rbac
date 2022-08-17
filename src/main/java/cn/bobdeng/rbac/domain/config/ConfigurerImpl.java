package cn.bobdeng.rbac.domain.config;

import cn.bobdeng.rbac.domain.parameter.Parameter;
import cn.bobdeng.rbac.domain.parameter.ParameterDescription;
import cn.bobdeng.rbac.domain.parameter.Parameters;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigurerImpl implements ConfigurationContext.Configurer {
    private Parameters parameters;

    public ConfigurerImpl(Parameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public List<Parameter> listParameters() {
        return parameters.list().collect(Collectors.toList());
    }

    @Override
    public void saveParameters(List<Parameter> parameters) {
        Map<String, String> values = parameters.stream().collect(Collectors.toMap(Parameter::identity, parameter -> parameter.description().getValue()));
        this.parameters.list()
                .filter(parameter -> parameter.isChanged(values))
                .forEach(parameter -> {
                    String key = parameter.identity();
                    this.parameters.save(new Parameter(parameter.getId(), new ParameterDescription(values.get(key), key)));
                });
    }

    @Override
    public Parameters parameters() {
        return parameters;
    }
}
