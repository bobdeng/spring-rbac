package cn.bobdeng.rbac.domain;

public interface HasOne<E extends Entity<?, ?>> {
    E get();
}
