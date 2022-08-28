package cn.bobdeng.rbac.domain.organization;

import cn.bobdeng.rbac.archtype.Entity;
import cn.bobdeng.rbac.archtype.HasOne;
import cn.bobdeng.rbac.domain.rbac.User;
import lombok.Getter;
import lombok.Setter;

public class Employee implements Entity<Integer, EmployeeDescription> {
    private Integer id;
    @Getter
    private EmployeeDescription description;
    @Setter
    private HasOne<Organization> organization;
    @Setter
    private HasOne<User> user;

    public Employee(User user) {
        this(user.identity());
    }

    public Employee(Integer id) {
        this.id = id;
    }

    public Organization organization() {
        return this.organization.get();
    }

    public User user() {
        return user.get();
    }

    @Override
    public Integer identity() {
        return id;
    }

    public Employee(Integer id, EmployeeDescription description) {
        this.id = id;
        this.description = description;
    }
}
