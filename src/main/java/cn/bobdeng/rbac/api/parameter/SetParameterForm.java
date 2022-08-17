package cn.bobdeng.rbac.api.parameter;

import cn.bobdeng.rbac.domain.config.Parameter;
import cn.bobdeng.rbac.domain.config.ParameterDescription;
import lombok.Setter;

@Setter
public class SetParameterForm {
    private String key;
    private String value;

    public Parameter toEntity() {
        return new Parameter(key,new ParameterDescription(value,key));
    }
}
