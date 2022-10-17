package cn.bobdeng.rbac.server.impl.configuration;

import cn.bobdeng.rbac.domain.config.ConfigurationContext;
import cn.bobdeng.rbac.domain.config.ConfigurerImpl;
import cn.bobdeng.rbac.domain.config.ParameterRepository;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationContextImpl implements ConfigurationContext {
    private ParameterRepository parameterRepository;

    public ConfigurationContextImpl(ParameterRepository parameterRepository) {
        this.parameterRepository = parameterRepository;
    }

    @Override
    public Configurer configurer() {
        return new ConfigurerImpl(parameterRepository);
    }
}
