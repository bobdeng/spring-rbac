package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.Tenant;
import cn.bobdeng.rbac.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class TenantRepositoryImpl implements TenantRepository {
    @Override
    public Tenant save(Tenant tenant) {
        return null;
    }

    @Override
    public Stream<Tenant> findByName(String name, int from, int to) {
        return null;
    }

    @Override
    public Optional<Tenant> findById(Integer id) {
        return Optional.empty();
    }
}
