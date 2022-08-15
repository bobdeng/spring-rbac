package cn.bobdeng.rbac.server.dao;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_rbac_employee")
public class EmployeeDO {
    @Id
    @Getter
    private Integer id;
    private Integer organizationId;
}
