package cn.bobdeng.rbac.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Data
@NoArgsConstructor
public class UserDescription {
    private String name;

    public UserDescription(String name) {

        this.name = name;
    }
}
