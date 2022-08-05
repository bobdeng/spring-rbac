package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.security.PermissionDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RestControllerAdvice
public class BaseController {
    @ExceptionHandler(PermissionDeniedException.class)
    public void onPermissionDenied(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().println("无权限");
    }
}
