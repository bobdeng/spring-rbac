package cn.bobdeng.rbac.domain.cbac;

import cn.bobdeng.rbac.archtype.Entity;

public class Context implements Entity<Integer, ContextDescription> {
    private Integer id;
    private ContextDescription description;

    @Override
    public Integer identity() {
        return id;
    }

    @Override
    public ContextDescription description() {
        return description;
    }

    public Context(ContextDescription contextDescription) {
        this.description = contextDescription;
    }

    public boolean match(ContextAuthority authority) {
        return description.match(authority);
    }

    public void validate() {
        description.validate();
    }
}
