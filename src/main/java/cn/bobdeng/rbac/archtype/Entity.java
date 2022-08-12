package cn.bobdeng.rbac.archtype;

public interface Entity<T, Description> {
    T identity();

    Description description();
}
