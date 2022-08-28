package cn.bobdeng.rbac.domain.organization;

import cn.bobdeng.rbac.archtype.Entity;
import cn.bobdeng.rbac.archtype.HasOne;
import lombok.Getter;
import lombok.Setter;

public class Employee implements Entity<Integer, EmployeeDescription> {
    private Integer id;
    @Getter
    private EmployeeDescription description;
    @Setter
    private HasOne<Organization> organization;

    public Organization organization() {
        return this.organization.get();
    }

    public Employee(Integer id, EmployeeDescription description) {
        this.id = id;
        this.description = description;
    }
}
