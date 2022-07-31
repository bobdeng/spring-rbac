package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.Domain;
import cn.bobdeng.rbac.domain.DomainRepository;
import cn.bobdeng.rbac.server.dao.DomainDAO;
import cn.bobdeng.rbac.server.dao.TenantDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class DomainRepositoryImpl implements DomainRepository {
    private final DomainDAO domainDAO;
    private TenantDAO tenantDAO;

    public DomainRepositoryImpl(DomainDAO domainDAO, TenantDAO tenantDAO) {
        this.domainDAO = domainDAO;
        this.tenantDAO = tenantDAO;
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
    public List<Domain> subList(int from, int to) {
        return null;
    }

    @Override
    public Stream<Domain> list() {
        return null;
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
        domain.setTenant(() -> tenantDAO.findById(domain.getDescription().getTenantId()).orElse(null));
        return domain;
    }

    @Override
    public int size() {
        return 0;
    }
}
