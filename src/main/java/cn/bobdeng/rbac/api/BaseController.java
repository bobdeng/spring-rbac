package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.archtype.FieldChecker;
import cn.bobdeng.rbac.archtype.FieldIllegalException;
import cn.bobdeng.rbac.security.PermissionDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RestControllerAdvice
@Slf4j
public class BaseController {
    @ExceptionHandler(PermissionDeniedException.class)
    public void onPermissionDenied(HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.warn("无权限：" + request.getAttribute("session"));
        sendResponseError(response, HttpServletResponse.SC_FORBIDDEN, "无权限");
    }

    private void sendResponseError(HttpServletResponse response, int status, String message) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(status);
        response.getWriter().print(message);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public void onObjectNotFound(HttpServletResponse response) throws IOException {
        sendResponseError(response, HttpServletResponse.SC_NOT_FOUND, "没有发现记录");
    }

    @ExceptionHandler(FieldIllegalException.class)
    public List<FieldChecker.FieldError> onInputError(FieldIllegalException e, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return e.getErrors();
    }

    @ExceptionHandler(RuntimeException.class)
    public void onUnexpectException(RuntimeException e, HttpServletResponse response) throws IOException {
        log.warn("error", e);
        String message = Optional.ofNullable(e.getMessage()).orElse(e.getClass().getName());
        sendResponseError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
    }
}
