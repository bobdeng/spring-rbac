package cn.bobdeng.rbac.domain;

import java.util.Optional;

public interface HasOne<E extends Entity<?, ?>> {
    E get();
}
