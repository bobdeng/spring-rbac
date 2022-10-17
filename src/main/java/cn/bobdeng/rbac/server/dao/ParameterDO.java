package cn.bobdeng.rbac.server.dao;

import cn.bobdeng.rbac.domain.config.Parameter;
import cn.bobdeng.rbac.domain.config.ParameterDescription;
import cn.bobdeng.rbac.domain.config.ParameterName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "t_rbac_parameter")
public class ParameterDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;
    private Integer tenantId;
    @Getter
    @Column(name = "param_key")
    private String key;
    @Column(name = "param_value")
    @Getter
    private String value;

    public ParameterDO(Integer id, Parameter entity) {
        this.key = entity.identity();
        this.value = entity.getDescription().getValue();
        this.id = id;
    }

    public ParameterDO(Integer tenantId, String key, String value) {
        this.tenantId = tenantId;
        this.key = key;
        this.value = value;
    }

    public Parameter toEntity(ParameterName name) {
        return new Parameter(key, new ParameterDescription(name.getName(), value));
    }
}
