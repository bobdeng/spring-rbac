package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.archtype.FieldChecker;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class DomainDescription {
    private String domain;
    private Integer tenantId;

    public DomainDescription(String domain, Integer tenantId) {
        this.domain = domain;
        this.tenantId = tenantId;
    }

    public void validate() {
        FieldChecker.of("domain", this.domain)
                .lengthLessThan(50, "域名最长50个字符")
                .notEmpty("域名不能为空")
                .throwIfHasErrors();
    }
}
