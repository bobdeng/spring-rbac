package cn.bobdeng.rbac.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;

@NoArgsConstructor
@AllArgsConstructor
public final class LoginNameDescription {
    @Getter
    private String name;
    @Getter
    private User user;
}
