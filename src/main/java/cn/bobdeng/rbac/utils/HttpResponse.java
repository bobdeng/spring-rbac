package cn.bobdeng.rbac.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HttpResponse {
    private int code;
    private String body;
}
