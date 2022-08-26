package cn.bobdeng.rbac.domain.cbac;

import lombok.Setter;

public class CbacImpl implements CbacContext.Cbac {
    @Setter
    private CbacContext.Contexts contexts;

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
