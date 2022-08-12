package cn.bobdeng.rbac.archtype;

import java.util.Optional;

public interface HasMany<ID, E extends Entity<ID, ?>> {
    default Many<E> findAll(){
        throw new RuntimeException("not implemented yet");
    }

    default Optional<E> findByIdentity(ID identifier){
        throw new RuntimeException("not implemented yet");
    }
}
