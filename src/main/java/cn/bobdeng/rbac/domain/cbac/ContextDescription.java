package cn.bobdeng.rbac.domain.cbac;

public class ContextDescription {
    private ContextObject object;
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
