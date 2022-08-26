package cn.bobdeng.rbac.domain.cbac;

import cn.bobdeng.rbac.archtype.EntityList;

import java.util.stream.Stream;

public interface CbacContext {
    interface Cbac {
        Context newContext(Context context);

        boolean isAllowed(ContextObject object, ContextAuthority authority);
    }

    interface Contexts extends EntityList<Integer, Context> {
        Stream<Context> findByObject(ContextObject object);
    }
}
