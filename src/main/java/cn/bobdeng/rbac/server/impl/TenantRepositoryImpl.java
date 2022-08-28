package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.archtype.HasMany;
import cn.bobdeng.rbac.archtype.Many;
import cn.bobdeng.rbac.archtype.Page;
import cn.bobdeng.rbac.domain.Domain;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.cbac.CbacContext;
import cn.bobdeng.rbac.domain.rbac.RbacContext;
import cn.bobdeng.rbac.server.dao.DomainDAO;
import cn.bobdeng.rbac.server.dao.TenantDAO;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenantRepositoryImpl implements TenantRepository {
    private final TenantDAO tenantDAO;
    private final DomainDAO domainDAO;
    private final RbacContext rbacContext;
    private final CbacContext cbacContext;

    public TenantRepositoryImpl(TenantDAO tenantDAO,
                                DomainDAO domainDAO,
                                RbacContext rbacContext, CbacContext cbacContext) {
        this.tenantDAO = tenantDAO;
        this.domainDAO = domainDAO;
        this.rbacContext = rbacContext;
        this.cbacContext = cbacContext;
    }

    @Override
    public Tenant save(Tenant tenant) {
        tenant = tenantDAO.save(tenant);
        return injectDependencies(tenant);
    }

    @Override
    public Page<Tenant> findByName(String name, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        org.springframework.data.domain.Page<Tenant> result = tenantDAO.findByDescriptionNameContaining(Optional.ofNullable(name).orElse(""), pageable);
        return new Page<>(result.getContent(), result.getTotalPages(), result.getTotalElements(), result.getNumber());
    }

    @Override
    public RbacContext rbacContext() {
        return rbacContext;
    }

    @Override
    public CbacContext cbacContext() {
        return cbacContext;
    }


    @Override
    public Optional<Tenant> findByIdentity(Integer integer) {
        return tenantDAO.findById(integer)
                .map(this::injectDependencies);
    }

    private Tenant injectDependencies(Tenant tenant) {
        tenant.setDomains(getDomains(tenant));
        return tenant;
    }

    private HasMany<Integer, Domain> getDomains(Tenant tenant) {
        return new HasMany<>() {
            @Override
            public Many<Domain> findAll() {
                return getManyDomains(tenant);
            }
        };
    }

    private Many<Domain> getManyDomains(Tenant tenant) {
        List<Domain> domains = domainDAO.findAllByDescriptionTenantId(tenant.identity());
        return domains::iterator;
    }

}
