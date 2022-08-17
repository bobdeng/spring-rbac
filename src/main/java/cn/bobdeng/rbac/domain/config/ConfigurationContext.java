package cn.bobdeng.rbac.domain.config;

import cn.bobdeng.rbac.domain.Tenant;

import java.util.List;

public interface ConfigurationContext {
    interface Configurer {
        List<Parameter> listParameters();

        void saveParameters(List<Parameter> parameters);

        Parameters parameters();
    }

    Configurer asConfigurer(Tenant tenant);
}
