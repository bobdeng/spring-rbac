package cn.bobdeng.rbac.domain.cbac;

import cn.bobdeng.rbac.archtype.Entity;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Context implements Entity<Integer, ContextDescription> {
    private Integer id;
    private ContextDescription description;

    public Context(Integer id, ContextDescription description) {
        this.id = id;
        this.description = description;
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
