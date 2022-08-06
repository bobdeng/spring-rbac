package cn.bobdeng.rbac.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class FieldIllegalException extends RuntimeException {
    private final List<FieldChecker.FieldError> errors;

    public FieldIllegalException(List<FieldChecker.FieldError> errors) {
        this.errors = errors;
    }
}
