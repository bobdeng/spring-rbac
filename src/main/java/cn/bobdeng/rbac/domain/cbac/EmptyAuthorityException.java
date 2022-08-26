package cn.bobdeng.rbac.domain.cbac;

public class EmptyAuthorityException extends RuntimeException {
    @Override
    public String getMessage() {
        return "权限不能为空";
    }
}
