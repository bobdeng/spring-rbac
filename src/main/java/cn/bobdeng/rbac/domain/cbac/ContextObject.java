package cn.bobdeng.rbac.domain.cbac;

import cn.bobdeng.rbac.archtype.FieldChecker;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class ContextObject {
    private String type;
    private String identity;

    public ContextObject(String type, String identity) {

        this.type = type;
        this.identity = identity;
    }

    public void validate() {
        if (FieldChecker.of("type", type)
                .notEmpty("")
                .concat("identity", identity)
                .notEmpty("")
                .getErrors().size() > 0) {
            throw new EmptyObjectException();
        }
    }
}
