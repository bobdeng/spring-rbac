package cn.bobdeng.rbac.domain.organization;

import cn.bobdeng.rbac.archtype.FieldIllegalException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrganizationDescriptionTest {
    @Test
    public void should_throw_when_length_bigger_than_20(){
        OrganizationDescription description = new OrganizationDescription("一二三四五一二三四五一二三四五一二三四五一", 1);
        assertThrows(FieldIllegalException.class, description::validate);
    }
}