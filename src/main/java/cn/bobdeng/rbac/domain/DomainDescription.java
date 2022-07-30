package cn.bobdeng.rbac.domain;

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
}
