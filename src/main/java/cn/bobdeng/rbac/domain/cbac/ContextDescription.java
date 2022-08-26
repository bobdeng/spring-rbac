package cn.bobdeng.rbac.domain.cbac;

import lombok.EqualsAndHashCode;
import lombok.Getter;
@EqualsAndHashCode
public class ContextDescription {
    @Getter
    private ContextObject object;
    @Getter
    private ContextAuthority authority;

    public ContextDescription(ContextObject contextObject, ContextAuthority contextAuthority) {

        this.object = contextObject;
        this.authority = contextAuthority;
    }

    public boolean match(ContextAuthority authority) {
        return this.authority.match(authority);
    }

    public void validate() {
        object.validate();
        authority.validate();
    }
}
