package cn.bobdeng.rbac.archtype;

public interface HasOne<E extends Entity<?, ?>> {
    E get();
}
