package cn.bobdeng.rbac.utils;

import cn.bobdeng.rbac.api.E2ETest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpClientImplTest extends E2ETest {
    @Test
    public void should_return_result() throws IOException {
        HttpClientImpl httpClient = new HttpClientImpl();
        httpClient.init();
        HttpRequest request = new HttpRequest(webDriverHandler.getBaseUrl() + "/test_http_client");
        HttpResponse response = httpClient.execute(request);
        assertEquals(200, response.getCode());
        assertEquals("hello", response.getBody());
    }

    @Test
    public void should_return_404_result() throws IOException {
        HttpClientImpl httpClient = new HttpClientImpl();
        httpClient.init();
        HttpRequest request = new HttpRequest(webDriverHandler.getBaseUrl() + "/test_http_client_not_found");
        HttpResponse response = httpClient.execute(request);
        assertEquals(404, response.getCode());
    }
}