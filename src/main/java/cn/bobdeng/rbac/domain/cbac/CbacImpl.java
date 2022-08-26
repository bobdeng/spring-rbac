package cn.bobdeng.rbac.domain.cbac;

public class CbacImpl implements CbacContext.Cbac {
    private CbacContext.Contexts contexts;

    public CbacImpl(CbacContext.Contexts contexts) {
        this.contexts = contexts;
    }

    @Override
    public Context newContext(Context context) {
        context.validate();
        return contexts.save(context);
    }

    @Override
    public boolean isAllowed(ContextObject object, ContextAuthority authority) {
        return contexts.findByObject(object).anyMatch(context -> context.match(authority));
    }
}
