package cn.bobdeng.rbac.domain;

public interface Entity<T, Description> {
    T identity();

    Description description();
}
