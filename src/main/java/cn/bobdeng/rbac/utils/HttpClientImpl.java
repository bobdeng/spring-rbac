package cn.bobdeng.rbac.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Objects;

@Service
public class HttpClientImpl implements HttpClient {
    OkHttpClient httpClient;

    @PostConstruct
    public void init() {
        httpClient = new OkHttpClient.Builder()
                .build();
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws IOException {
        Request okHttpRequest = new Request.Builder()
                .get()
                .url(request.getUrl())
                .build();
        Response response = httpClient.newCall(okHttpRequest).execute();
        return new HttpResponse(response.code(), Objects.requireNonNull(response.body()).string());
    }
}
