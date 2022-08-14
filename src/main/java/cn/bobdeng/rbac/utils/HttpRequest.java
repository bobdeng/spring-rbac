package cn.bobdeng.rbac.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class HttpRequest {
    private String url;

    public HttpRequest(String url) {
        this.url = url;
    }
}
