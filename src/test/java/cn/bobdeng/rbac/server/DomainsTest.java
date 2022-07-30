package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.server.dao.DomainDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class DomainsTest {
    @Autowired
    DomainRepository domainRepository;
    @Autowired
    DomainDAO domainDAO;
    @Autowired
    TenantRepository tenantRepository;

    @Test
    public void save_domain() {
        Tenant tenant = tenantRepository.save(new Tenant(new TenantDescription("Tenant1")));
        Domains domains = new Domains(domainRepository);

        Domain domain = domains.newDomain(new DomainDescription("localhost", tenant.getId()));
        assertNotNull(domain.identity());

    }
}
