package cn.bobdeng.rbac.server.dao;

import cn.bobdeng.rbac.domain.rbac.ThirdDescription;
import cn.bobdeng.rbac.domain.rbac.ThirdIdentity;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_rbac_third_identity")
public class ThirdIdentityDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String thirdName;
    private String identity;
    private Integer userId;
    private Integer tenantId;

    public ThirdIdentity toEntity() {
        return new ThirdIdentity(id, new ThirdDescription(identity, thirdName, userId));
    }
}
