package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.domain.Domain;
import cn.bobdeng.rbac.domain.DomainRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class DomainRepositoryImpl implements DomainRepository {
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
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
