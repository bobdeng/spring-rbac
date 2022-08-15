package cn.bobdeng.rbac.api.oauth;

import cn.bobdeng.rbac.utils.HttpClient;
import cn.bobdeng.rbac.utils.HttpRequest;
import cn.bobdeng.rbac.utils.HttpResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Primary
public class MockHttpClient implements HttpClient {
    private HttpClient httpClient;

    @Override
    public HttpResponse execute(HttpRequest request) throws IOException {
        return httpClient.execute(request);
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
