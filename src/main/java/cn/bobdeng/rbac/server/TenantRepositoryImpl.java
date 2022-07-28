package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.Tenant;
import cn.bobdeng.rbac.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class TenantRepositoryImpl implements TenantRepository {
    private TenantDAO tenantDAO;
    private UserDAO userDAO;

    public TenantRepositoryImpl(TenantDAO tenantDAO, UserDAO userDAO) {
        this.tenantDAO = tenantDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Tenant save(Tenant tenant) {
        tenant = tenantDAO.save(tenant);
        tenant.setUsers(new TenantUsers(tenant, userDAO));
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

    @Override
    public List<Tenant> subList(int from, int to) {
        return null;
    }

    @Override
    public Stream<Tenant> list() {
        return null;
    }

    @Override
    public Optional<Tenant> findByIdentity(Integer integer) {
        return tenantDAO.findById(integer)
                .map(tenant -> {
                    tenant.setUsers(new TenantUsers(tenant, userDAO));
                    return tenant;
                });
    }

    @Override
    public int size() {
        return 0;
    }
}
