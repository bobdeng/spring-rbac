package cn.bobdeng.rbac.utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class HttpRequest {
    private String url;

    public HttpRequest(String url) {
        this.url = url;
    }
}
