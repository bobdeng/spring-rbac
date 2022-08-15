package cn.bobdeng.rbac.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class HttpResponse {
    private int code;
    private String body;
}
