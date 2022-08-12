package cn.bobdeng.rbac.archtype;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface EntityList<ID, E extends Entity<ID, ?>> {
    default List<E> subList(int from, int to) {
        throw new RuntimeException("not implemented yet");
    }

    default Stream<E> list() {
        throw new RuntimeException("not implemented yet");
    }

    default Optional<E> findByIdentity(ID id) {
        throw new RuntimeException("not implemented yet");
    }

    default E save(E entity) {
        throw new RuntimeException("not implemented yet");
    }

    default int size() {
        throw new RuntimeException("not implemented yet");
    }
}
