package cn.bobdeng.rbac.api;

import lombok.Data;

@Data
public class Session {
    private String tenantName = "租户1";
    private String name = "我的名字";
    private Integer id = 1;
}
