package cn.bobdeng.rbac.archtype;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FieldChecker {
    private final Object value;
    private final String fieldName;
    @Getter
    private final List<FieldError> errors;

    public FieldChecker(String fieldName, Object value) {
        this(fieldName, value, new ArrayList<>());
    }

    public FieldChecker(String name, Object value, List<FieldError> errors) {
        this.value = value;
        this.fieldName = name;
        this.errors = errors;
    }

    public static FieldChecker of(String fieldName, Object value) {
        return new FieldChecker(fieldName, value);
    }

    public FieldChecker notNull(String message) {
        return check(() -> value == null, message);
    }

    public FieldChecker lengthLessThan(int len, String message) {
        return check(() -> value != null && value.toString().length() > len, message);
    }

    public FieldChecker notEmpty(String message) {
        return check(this::isEmpty, message);
    }

    private boolean isEmpty() {
        if (value == null) {
            return true;
        }
        if (value instanceof List) {
            return ((List<?>) value).size() == 0;
        }
        return value.equals("");
    }

    public FieldChecker check(Supplier<Boolean> illegalDetector, String message) {
        if (illegalDetector.get()) {
            errors.add(new FieldError(fieldName, message));
        }
        return this;
    }

    public void throwIfHasErrors() {
        if (this.errors.size() > 0) {
            throw new FieldIllegalException(this.errors);
        }
    }

    public FieldChecker concat(String name, String value) {
        return new FieldChecker(name, value, getErrors());
    }

    public FieldChecker lengthBiggerThan(int len, String message) {
        return check(() -> value != null && value.toString().length() < len, message);
    }

    @Getter
    static public class FieldError {
        private String field;
        private String error;

        public FieldError(String field, String error) {
            this.field = field;
            this.error = error;
        }
    }
}
