package cn.bobdeng.rbac.domain.config;

import cn.bobdeng.rbac.domain.Tenant;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigurerImpl implements ConfigurationContext.Configurer {
    private ConfigurationContext configurationContext;
    private Tenant tenant;

    public ConfigurerImpl(ConfigurationContext configurationContext, Tenant tenant) {
        this.configurationContext = configurationContext;
        this.tenant = tenant;
    }

    @Override
    public List<Parameter> listParameters() {
        return parameters().list().collect(Collectors.toList());
    }

    @Override
    public void saveParameters(List<Parameter> parameters) {
        parameters.stream().map(Parameter::getDescription)
                .forEach(ParameterDescription::validate);
        Map<String, String> values = parameters.stream().collect(Collectors.toMap(Parameter::identity, parameter -> parameter.description().getValue()));
        parameters().list()
                .filter(parameter -> parameter.isChanged(values))
                .forEach(parameter -> {
                    String key = parameter.identity();
                    parameters().save(new Parameter(parameter.getId(), new ParameterDescription(values.get(key), key)));
                });
    }

    @Override
    public Parameters parameters() {
        return configurationContext.parameters(tenant);
    }
}
