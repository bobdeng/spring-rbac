package cn.bobdeng.rbac.domain.rbac;

import cn.bobdeng.rbac.archtype.Entity;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Lock implements Entity<Integer, LockDescription> {
    private Integer id;
    private LockDescription description;

    @Override
    public Integer identity() {
        return id;
    }

    @Override
    public LockDescription description() {
        return description;
    }

    public Lock(Integer id, LockDescription description) {

        this.id = id;
        this.description = description;
    }
}
