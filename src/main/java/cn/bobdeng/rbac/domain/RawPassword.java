package cn.bobdeng.rbac.domain;

import lombok.Data;

@Data
public class RawPassword {
    private String rawPassword;

    public RawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }
}
