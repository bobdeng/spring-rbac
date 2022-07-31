package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.api.pages.NewDomainPage;
import cn.bobdeng.rbac.domain.Domain;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantDescription;
import cn.bobdeng.rbac.domain.TenantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewDomainTest extends E2ETest {
    @Autowired
    TenantRepository tenantRepository;

    @BeforeEach
    public void setup() {
        adminLogin();
        clearTable("t_rbac_domain");
    }

    @Test
    public void should_add_domain() {
        Tenant tenant = new Tenant(new TenantDescription("租户1"));
        tenant = tenantRepository.save(tenant);
        NewDomainPage newDomainPage = new NewDomainPage(webDriverHandler);
        newDomainPage.open(tenant);
        newDomainPage.inputDomain("www.test.com");
        newDomainPage.submit();

        List<Domain> domains = tenant.domains();
        assertEquals(1, domains.size());
    }
}
