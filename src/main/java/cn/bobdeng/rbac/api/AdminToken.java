package cn.bobdeng.rbac.api;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
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
