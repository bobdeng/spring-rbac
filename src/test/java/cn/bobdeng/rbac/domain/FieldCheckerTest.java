package cn.bobdeng.rbac.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FieldCheckerTest {
    @Test
    public void check_fluent(){
        List<FieldChecker.FieldError> errors = FieldChecker.of("name", "123")
                .notEmpty("not empty")
                .concat("name1", "456")
                .getErrors();
        assertEquals(0,errors.size());
    }
    @Test
    public void multi_field_error(){
        List<FieldChecker.FieldError> errors = FieldChecker.of("name", null)
                .notNull("名字不能为空")
                .concat("name1", null)
                .notNull("名字1不能为空")
                .getErrors();
        assertEquals(2,errors.size());
        assertEquals("name",errors.get(0).getField());
        assertEquals("name1",errors.get(1).getField());
        assertEquals("名字不能为空",errors.get(0).getError());
        assertEquals("名字1不能为空",errors.get(1).getError());
    }
}