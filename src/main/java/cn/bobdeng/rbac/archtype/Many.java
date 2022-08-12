package cn.bobdeng.rbac.archtype;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Many<E extends Entity<?, ?>> extends Iterable<E> {
    default int size(){
        throw new RuntimeException("not implemented yet");
    }

    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
}
