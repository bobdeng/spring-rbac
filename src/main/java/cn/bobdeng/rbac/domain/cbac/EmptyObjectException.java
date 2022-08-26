package cn.bobdeng.rbac.domain.cbac;

public class EmptyObjectException extends RuntimeException{
    @Override
    public String getMessage() {
        return "对象不能为空";
    }
}
