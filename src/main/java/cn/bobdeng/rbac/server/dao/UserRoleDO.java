package cn.bobdeng.rbac.server.dao;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_rbac_user_role")
public class UserRoleDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;
    private Integer userId;
    @Getter
    private Integer roleId;

}
