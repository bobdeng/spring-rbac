package cn.bobdeng.rbac.utils;

import cn.bobdeng.rbac.archtype.SystemDate;
import cn.bobdeng.rbac.security.Session;
import cn.bobdeng.rbac.security.SessionStore;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class TenantInterceptor extends EmptyInterceptor implements HibernatePropertiesCustomizer {
    private final SessionStore sessionStore;
    private final List<FieldInterceptor> interceptors;
    private final TableHasNoTenantId tableHasNoTenantId;

    public TenantInterceptor(SessionStore sessionStore, TableHasNoTenantId tableHasNoTenantId) {
        this.sessionStore = sessionStore;
        interceptors = Arrays.asList(
                new FieldInterceptor(() -> sessionStore.getTenant().identity(), "tenantId"),
                new FieldInterceptor(SystemDate::now, "createdAt"),
                new FieldInterceptor(() -> sessionStore.get().map(Session::userId).orElse(null), "createdBy"));

        this.tableHasNoTenantId = tableHasNoTenantId;
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        log.info("on save " + entity.getClass().getName());
        if (tryInteceptor(state, propertyNames)) {
            return false;
        }
        return true;
    }

    private boolean tryInteceptor(Object[] state, String[] propertyNames) {
        if (sessionStore.getTenant() == null) {
            return true;
        }
        if (interceptors.stream().noneMatch(it -> it.needInterceptor(propertyNames))) {
            return true;
        }
        interceptors.forEach(interceptor -> interceptor.intercept(propertyNames, state));
        return false;
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if (tryInteceptor(currentState, propertyNames)) {
            return false;
        }
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    @Override
    public String onPrepareStatement(String sql) {
        if (sessionStore.getTenant() == null || !sql.startsWith("select")) {
            return sql;
        }
        return new SQLInterceptor(sql).intercept(sessionStore.getTenant(), tableHasNoTenantId.tables());
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.session_factory.interceptor", this);
    }


    public static final class FieldInterceptor {
        private final Supplier<Object> valueSupplier;
        private final String fieldName;

        public FieldInterceptor(Supplier<Object> valueSupplier,
                                String fieldName) {
            this.valueSupplier = valueSupplier;
            this.fieldName = fieldName;
        }

        public void intercept(String[] propertyNames, Object[] state) {
            for (int i = 0; i < propertyNames.length; i++) {
                if (propertyNames[i].equals(fieldName)) {
                    if (state[i] != null) {
                        continue;
                    }
                    state[i] = valueSupplier.get();
                }
            }
        }

        public boolean needInterceptor(String[] propertyNames) {
            return Arrays.asList(propertyNames).contains(fieldName);
        }

    }
}
