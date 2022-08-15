package cn.bobdeng.rbac.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public final class LoginNameDescription {
    @Getter
    private String name;
    @Getter
    private Integer userId;
}
