package cn.bobdeng.rbac.domain;

import java.util.Optional;

public interface HasOptional<E extends Entity<?, ?>> {
    Optional<E> get();
}
