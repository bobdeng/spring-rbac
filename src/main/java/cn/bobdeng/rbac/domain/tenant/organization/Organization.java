package cn.bobdeng.rbac.domain.tenant.organization;

import cn.bobdeng.rbac.domain.Entity;
import cn.bobdeng.rbac.domain.EntityList;
import cn.bobdeng.rbac.domain.User;
import lombok.Getter;
import lombok.Setter;

public class Organization implements Entity<Integer, OrganizationDescription> {
    @Getter
    private Integer id;
    @Getter
    private OrganizationDescription description;
    @Setter
    private Employees employees;

    public Organization(Integer id, OrganizationDescription description) {
        this.id = id;
        this.description = description;
    }

    public Organization(OrganizationDescription description) {
        this.description = description;
    }

    @Override
    public Integer identity() {
        return id;
    }

    @Override
    public OrganizationDescription description() {
        return description;
    }

    public Employees employees() {
        return employees;
    }

    public void removeEmployee(Integer userId) {
        employees.findByIdentity(userId).ifPresent(user -> employees.delete(user));
    }

    public interface Employees extends EntityList<Integer, User> {

        void delete(User user);
    }
}
