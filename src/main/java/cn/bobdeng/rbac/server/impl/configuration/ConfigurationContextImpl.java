package cn.bobdeng.rbac.server.impl.configuration;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.config.ConfigurationContext;
import cn.bobdeng.rbac.domain.config.ConfigurerImpl;
import cn.bobdeng.rbac.domain.config.Parameters;
import cn.bobdeng.rbac.server.dao.ParameterDAO;
import cn.bobdeng.rbac.server.impl.ExternalParameters;
import cn.bobdeng.rbac.server.impl.ParametersImpl;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationContextImpl implements ConfigurationContext {
    private final ParameterDAO parameterDAO;
    private final ExternalParameters externalParameters;

    public ConfigurationContextImpl(ParameterDAO parameterDAO, ExternalParameters externalParameters) {
        this.parameterDAO = parameterDAO;
        this.externalParameters = externalParameters;
    }

    @Override
    public Parameters parameters(Tenant te) {
        return new ParametersImpl(te, parameterDAO, externalParameters);
    }

    @Override
    public Configurer asConfigurer(Tenant tenant) {
        return new ConfigurerImpl(this, tenant);
    }
}
