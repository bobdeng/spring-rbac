package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.Domain;
import cn.bobdeng.rbac.domain.DomainRepository;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.server.dao.DomainDAO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DomainRepositoryImpl implements DomainRepository {
    private final DomainDAO domainDAO;
    private TenantRepository tenantRepository;

    public DomainRepositoryImpl(DomainDAO domainDAO, TenantRepository tenantRepository) {
        this.domainDAO = domainDAO;
        this.tenantRepository = tenantRepository;
    }

    @Override
    public Optional<Domain> findByDomain(String domain) {
        return domainDAO.findByDescriptionDomain(domain)
                .map(this::inject);
    }

    @Override
    public void delete(Integer id) {
        domainDAO.deleteById(id);
    }


    @Override
    public Optional<Domain> findByIdentity(Integer integer) {
        return domainDAO.findById(integer).map(this::inject);
    }

    @Override
    public Domain save(Domain entity) {
        return inject(domainDAO.save(entity));
    }

    private Domain inject(Domain domain) {
        domain.setTenant(() -> tenantRepository.findByIdentity(domain.description().getTenantId()).orElse(null));
        return domain;
    }

}
