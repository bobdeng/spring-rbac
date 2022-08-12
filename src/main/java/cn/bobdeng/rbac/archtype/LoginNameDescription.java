package cn.bobdeng.rbac.archtype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public final class LoginNameDescription {
    @Getter
    private String name;
    @Getter
    private Integer userId;
}
