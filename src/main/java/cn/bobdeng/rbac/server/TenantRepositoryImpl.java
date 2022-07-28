package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.Tenant;
import cn.bobdeng.rbac.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class TenantRepositoryImpl implements TenantRepository {
    private TenantDAO tenantDAO;

    public TenantRepositoryImpl(TenantDAO tenantDAO) {
        this.tenantDAO = tenantDAO;
    }

    @Override
    public Tenant save(Tenant tenant) {
        tenant = tenantDAO.save(tenant);
        tenant.setUsers(new TenantUsers(tenant));
        return tenant;
    }

    @Override
    public Stream<Tenant> findByName(String name, int from, int to) {
        return tenantDAO.findByName(name).skip(from).limit(to - from);
    }

    @Override
    public Optional<Tenant> findById(Integer id) {
        return Optional.empty();
    }
}
