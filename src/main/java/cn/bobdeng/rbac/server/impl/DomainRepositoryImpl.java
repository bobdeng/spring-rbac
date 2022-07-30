package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.Domain;
import cn.bobdeng.rbac.domain.DomainRepository;
import cn.bobdeng.rbac.domain.HasOne;
import cn.bobdeng.rbac.domain.Tenant;
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
        return Optional.empty();
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
        return Optional.empty();
    }

    @Override
    public Domain save(Domain entity) {
        Domain saved = domainDAO.save(entity);
        saved.setTenant(() -> tenantDAO.findById(saved.getDescription().getTenantId()).orElse(null));
        return saved;
    }

    @Override
    public int size() {
        return 0;
    }
}
