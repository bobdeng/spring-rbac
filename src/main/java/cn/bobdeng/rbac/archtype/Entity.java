package cn.bobdeng.rbac.archtype;

public interface Entity<T, Description> {
    default T identity() {
        throw new RuntimeException("not implemented yet");
    }

    default Description description() {
        throw new RuntimeException("not implemented yet");
    }
}
