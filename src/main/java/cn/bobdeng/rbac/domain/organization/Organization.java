package cn.bobdeng.rbac.domain.organization;

import cn.bobdeng.rbac.archtype.Entity;
import cn.bobdeng.rbac.archtype.EntityList;
import cn.bobdeng.rbac.archtype.HasOne;
import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.rbac.User;
import lombok.Getter;
import lombok.Setter;

public class Organization implements Entity<Integer, OrganizationDescription> {
    @Getter
    private Integer id;
    @Getter
    private OrganizationDescription description;
    @Setter
    private OrganizationContext organizationContext;
    @Setter
    private HasOne<Tenant> tenant;

    public Organization(Integer id, OrganizationDescription description) {
        this.id = id;
        this.description = description;
    }

    public Tenant tenant() {
        return this.tenant.get();
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
        return organizationContext.employees(this);
    }

    public void removeEmployee(Integer userId) {
        employees().findByIdentity(userId).ifPresent(user -> employees().delete(user));
    }

    public void addEmployee(User user) {
        employees().save(new Employee(user));
    }

    public interface Employees extends EntityList<Integer, Employee> {

        void delete(Employee employee);
    }
}
