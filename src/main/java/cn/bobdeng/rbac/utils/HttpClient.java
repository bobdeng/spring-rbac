package cn.bobdeng.rbac.utils;

import java.io.IOException;

public interface HttpClient {
    HttpResponse execute(HttpRequest request) throws IOException;

}
