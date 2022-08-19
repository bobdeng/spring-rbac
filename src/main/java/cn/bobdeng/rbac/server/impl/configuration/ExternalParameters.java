package cn.bobdeng.rbac.server.impl.configuration;

import cn.bobdeng.rbac.domain.config.ParameterName;

import java.util.List;

public interface ExternalParameters {
    List<ParameterName> parameters();
}
