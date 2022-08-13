package cn.bobdeng.rbac.server.dao;

import cn.bobdeng.rbac.domain.third.ThirdDescription;
import cn.bobdeng.rbac.domain.third.ThirdIdentity;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
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
