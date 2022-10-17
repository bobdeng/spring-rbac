package cn.bobdeng.rbac.server.impl.configuration;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.config.*;
import cn.bobdeng.rbac.server.dao.ParameterDAO;
import cn.bobdeng.rbac.server.dao.ParameterDO;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class ParameterRepositoryImpl implements ParameterRepository {
    private final ParameterDAO parameterDAO;
    private final ExternalParameters externalParameters;

    public ParameterRepositoryImpl(ParameterDAO parameterDAO, ExternalParameters externalParameters) {
        this.parameterDAO = parameterDAO;
        this.externalParameters = externalParameters;
    }

    private Stream<ParameterName> parameterNameStream() {
        return Stream.concat(externalParameters.parameters().stream(), BaseParameters.list().stream());
    }

    @Override
    public Stream<Parameter> list() {
        Map<String, ParameterDO> parameters = StreamSupport.stream(parameterDAO.findAll().spliterator(), false)
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
    public void save(Parameter entity) {
        Integer id = parameterDAO.findByKey(entity.identity()).map(ParameterDO::getId)
                .orElse(null);
        parameterDAO.save(new ParameterDO(id, entity));
    }

    @Override
    public Optional<Parameter> findByName(String id) {
        return parameterNameStream().filter(it -> it.getKey().equals(id))
                .findFirst()
                .map(name -> parameterDAO.findByKey(name.getKey())
                        .map(parameterDO -> parameterDO.toEntity(name))
                        .orElseGet(() -> getDefaultParameter(name))
                );
    }
}
