package cn.bobdeng.rbac.archtype;

import java.util.Optional;

public interface HasOptional<E extends Entity<?, ?>> {
    Optional<E> get();
}
