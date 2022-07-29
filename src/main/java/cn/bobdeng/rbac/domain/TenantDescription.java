package cn.bobdeng.rbac.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Data
@NoArgsConstructor
public class TenantDescription {
    private String name;

    public TenantDescription(String name) {
        this.name = name;
    }
}
