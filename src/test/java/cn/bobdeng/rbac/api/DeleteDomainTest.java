package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.api.pages.ListTenantDomainPage;
import cn.bobdeng.rbac.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DeleteDomainTest extends E2ETest {
    @Autowired
    TenantRepository tenantRepository;
    @Autowired
    DomainRepository domainRepository;

    @BeforeEach
    public void setup() {
        adminLogin();
    }

    @Test
    public void should_delete_domain() {
        Tenant tenant = new Tenant(new TenantDescription(""));
        tenant = tenantRepository.save(tenant);
        Domain domain = domainRepository.save(new Domain(new DomainDescription("www.test.com", tenant.identity())));
        ListTenantDomainPage listTenantDomainPage = new ListTenantDomainPage(webDriverHandler);
        listTenantDomainPage.open(tenant);

        listTenantDomainPage.delete(0);
        WebDriverHandler.WEBDRIVER.switchTo().alert().accept();

        assertEquals(0, listTenantDomainPage.domains().size());
    }
}
