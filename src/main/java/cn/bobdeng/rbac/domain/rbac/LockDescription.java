package cn.bobdeng.rbac.domain.rbac;

import cn.bobdeng.rbac.archtype.SystemDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class LockDescription {
    private long lockAt;

    public LockDescription() {
        this.lockAt = SystemDate.now();
    }

    public LockDescription(long lockAt) {
        this.lockAt = lockAt;
    }
}
