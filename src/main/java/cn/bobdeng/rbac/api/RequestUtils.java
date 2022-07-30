package cn.bobdeng.rbac.api;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestUtils {
    private RequestUtils(){}
    public static String getHost(HttpServletRequest request) throws MalformedURLException {
        String requestPath = request.getRequestURL().toString();
        URL url = new URL(requestPath);
        return url.getHost();
    }
}
