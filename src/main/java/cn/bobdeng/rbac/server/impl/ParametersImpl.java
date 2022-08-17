package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.parameter.Parameter;
import cn.bobdeng.rbac.domain.parameter.ParameterDescription;
import cn.bobdeng.rbac.domain.parameter.Parameters;
import cn.bobdeng.rbac.server.dao.ParameterDAO;
import cn.bobdeng.rbac.server.dao.ParameterDO;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParametersImpl implements Parameters {
    private final Tenant tenant;
    private final ParameterDAO parameterDAO;
    private final ExternalParameters externalParameters;

    public ParametersImpl(Tenant tenant, ParameterDAO parameterDAO, ExternalParameters externalParameters) {
        this.tenant = tenant;
        this.parameterDAO = parameterDAO;
        this.externalParameters = externalParameters;
    }

    @Override
    public Stream<Parameter> list() {
        Map<String, ParameterDO> parameters = parameterDAO.findAllByTenantId(tenant.identity())
                .stream()
                .collect(Collectors.toMap(ParameterDO::getKey, it -> it));
        return externalParameters.parameters().stream()
                .map(name -> Optional.ofNullable(parameters.get(name.getKey()))
                        .map(parameterDO -> parameterDO.toEntity(name)).orElseGet(() -> new Parameter(new ParameterDescription(name.getName(), name.getDefaultValue(), name.getKey()))
                        ));
    }

    @Override
    public Parameter save(Parameter entity) {
        ParameterName parameterName = externalParameters.parameters().stream().filter(it -> it.getKey().equals(entity.description().getKey()))
                .findFirst().orElseThrow();
        return parameterDAO.save(new ParameterDO(entity, tenant)).toEntity(parameterName);
    }
}
