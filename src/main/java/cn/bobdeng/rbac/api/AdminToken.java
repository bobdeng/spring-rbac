package cn.bobdeng.rbac.api;

import lombok.Getter;

@Getter
public class AdminToken {
    public AdminToken() {
    }

    @Override
    public String toString() {
        return new JwtToken<>(this).toString();
    }
}
