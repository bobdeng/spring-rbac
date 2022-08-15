package cn.bobdeng.rbac.api;

import lombok.Getter;

@Getter
public class AdminToken {
    private long timestamp;

    public AdminToken() {
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return new JwtToken<>(this).toString();
    }
}
