package cn.bobdeng.rbac.api.parameter;

import cn.bobdeng.rbac.api.E2ETest;
import cn.bobdeng.rbac.api.UserWithTenantFixture;
import cn.bobdeng.rbac.server.dao.ParameterDAO;
import cn.bobdeng.rbac.server.dao.ParameterDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParametersTest extends E2ETest {
    @Autowired
    UserWithTenantFixture userWithTenantFixture;
    @Autowired
    ParameterDAO parameterDAO;

    @BeforeEach
    public void setup() {
        clearTable.clearTable("t_rbac_parameter");
        userWithTenantFixture.init();
        userLogin(userWithTenantFixture.user());
    }

    @Test
    public void 当没有配置参数() {
        ParametersPage parametersPage = new ParametersPage(webDriverHandler);
        parametersPage.open();
        parametersPage.waitUntil(() -> parametersPage.hasText("系统参数1"), 1000);
        String value = parametersPage.parameterValue(0);
        assertEquals("102", value);
    }

    @Test
    public void 当配置了参数读取配置参数() {
        parameterDAO.save(new ParameterDO(userWithTenantFixture.getTenant().identity(),"param.key1", "99"));
        ParametersPage parametersPage = new ParametersPage(webDriverHandler);
        parametersPage.open();
        parametersPage.waitUntil(() -> parametersPage.hasText("系统参数1"), 1000);
        String value = parametersPage.parameterValue(0);
        assertEquals("99", value);
    }

    @Test
    public void 当保存参数(){
        ParametersPage parametersPage = new ParametersPage(webDriverHandler);
        parametersPage.open();
        parametersPage.waitUntil(() -> parametersPage.hasText("系统参数1"), 1000);
        parametersPage.inputValue(0,"99");
        parametersPage.save();
        parametersPage.waitUntilNoButtonSpin();
        parametersPage.waitUntil(()->parametersPage.hasText("保存成功"),1000);
        List<ParameterDO> parameters = parameterDAO.findAllByTenantId(userWithTenantFixture.getTenant().identity());
        assertEquals(1,parameters.size());
    }
}
