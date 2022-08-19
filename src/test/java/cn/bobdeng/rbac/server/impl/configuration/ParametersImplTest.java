package cn.bobdeng.rbac.server.impl.configuration;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.config.BaseParameters;
import cn.bobdeng.rbac.domain.config.Parameter;
import cn.bobdeng.rbac.server.dao.ParameterDAO;
import cn.bobdeng.rbac.server.dao.ParameterDO;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ParametersImplTest {
    private ParametersImpl parameters;
    private ParameterDAO parameterDao;
    private Tenant tenant;

    @BeforeEach
    private void setup() {
        tenant = new Tenant(100, null);
        parameterDao = mock(ParameterDAO.class);
        ExternalParameters externalParameters = mock(ExternalParameters.class);
        parameters = new ParametersImpl(tenant, parameterDao, externalParameters);
    }

    @Test
    public void should_return_default_value_when_not_set() {
        List<Parameter> list = parameters.list().toList();
        assertEquals(BaseParameters.list().size(), list.size());
        assertEquals("strong", list.get(0).getDescription().getValue());
        assertEquals("strong", parameters.findByIdentity(BaseParameters.PASSWORD_POLICY).get().getDescription().getValue());
    }

    @Test
    public void should_return_setted_value_when_set() {
        ParameterDO weak = new ParameterDO(tenant.identity(), BaseParameters.PASSWORD_POLICY, "weak");
        when(parameterDao.findByKeyAndTenantId(BaseParameters.PASSWORD_POLICY, tenant.identity()))
                .thenReturn(Optional.of(weak));
        when(parameterDao.findAllByTenantId(tenant.identity())).thenReturn(
                Arrays.asList(weak)
        );
        List<Parameter> list = parameters.list().toList();
        assertEquals(BaseParameters.list().size(), list.size());
        assertEquals("weak", list.get(0).getDescription().getValue());
        assertEquals("weak", parameters.findByIdentity(BaseParameters.PASSWORD_POLICY).get().getDescription().getValue());
    }

}