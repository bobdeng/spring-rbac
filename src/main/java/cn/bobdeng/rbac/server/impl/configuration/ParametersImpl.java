package cn.bobdeng.rbac.server.impl.configuration;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.config.*;
import cn.bobdeng.rbac.server.dao.ParameterDAO;
import cn.bobdeng.rbac.server.dao.ParameterDO;
import org.jetbrains.annotations.NotNull;

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

    private Stream<ParameterName> parameterNameStream() {
        return Stream.concat(externalParameters.parameters().stream(), BaseParameters.list().stream());
    }

    @Override
    public Stream<Parameter> list() {
        Map<String, ParameterDO> parameters = parameterDAO.findAllByTenantId(tenant.identity())
                .stream()
                .collect(Collectors.toMap(ParameterDO::getKey, it -> it));
        return parameterNameStream()
                .map(name -> Optional.ofNullable(parameters.get(name.getKey()))
                        .map(parameterDO -> parameterDO.toEntity(name))
                        .orElseGet(() -> getDefaultParameter(name)));
    }

    private Parameter getDefaultParameter(ParameterName name) {
        return new Parameter(name.getKey(), new ParameterDescription(name.getName(), name.getDefaultValue()));
    }

    @Override
    public Parameter save(Parameter entity) {
        ParameterName parameterName = getParameterName(entity.identity());
        Integer id = parameterDAO.findByKeyAndTenantId(entity.identity(), tenant.identity()).map(ParameterDO::getId)
                .orElse(null);
        return parameterDAO.save(new ParameterDO(id, entity, tenant)).toEntity(parameterName);
    }

    private ParameterName getParameterName(String key) {
        return parameterNameStream().filter(it -> it.getKey().equals(key))
                .findFirst().orElseThrow();
    }

    @Override
    public Optional<Parameter> findByIdentity(String id) {
        return parameterNameStream().filter(it -> it.getKey().equals(id))
                .findFirst()
                .map(name -> parameterDAO.findByKeyAndTenantId(name.getKey(), tenant.identity())
                        .map(parameterDO -> parameterDO.toEntity(name))
                        .orElseGet(() -> getDefaultParameter(name))
                );
    }
}
