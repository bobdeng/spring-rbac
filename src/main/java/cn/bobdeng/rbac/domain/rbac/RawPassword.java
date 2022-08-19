package cn.bobdeng.rbac.domain.rbac;

import lombok.Getter;

@Getter
public class RawPassword {
    private String rawPassword;

    public RawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }
}
