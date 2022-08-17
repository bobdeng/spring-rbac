package cn.bobdeng.rbac.server.impl;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
public class ExternalParametersImpl implements ExternalParameters {
    @Override
    public List<ParameterName> parameters() {
        return Collections.emptyList();
    }
}
