package cn.bobdeng.rbac.utils;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class EmptyTableHasNoTenantId implements TableHasNoTenantId {
    @Override
    public Set<String> tables() {
        return Collections.emptySet();
    }
}
