package cn.bobdeng.rbac.domain;

import lombok.Getter;

@Getter
public class RawPassword {
    private String rawPassword;

    public RawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }
}
