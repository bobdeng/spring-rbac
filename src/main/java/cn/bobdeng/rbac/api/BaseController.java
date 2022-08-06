package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.domain.FieldChecker;
import cn.bobdeng.rbac.domain.FieldIllegalException;
import cn.bobdeng.rbac.security.PermissionDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RestControllerAdvice
@Slf4j
public class BaseController {
    @ExceptionHandler(PermissionDeniedException.class)
    public void onPermissionDenied(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().println("无权限");
    }

    @ExceptionHandler(FieldIllegalException.class)
    public List<FieldChecker.FieldError> onInputError(FieldIllegalException e, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return e.getErrors();
    }

    @ExceptionHandler(RuntimeException.class)
    public void onUnexpectException(RuntimeException e, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        e.printStackTrace(response.getWriter());
    }
}
