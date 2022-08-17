package cn.bobdeng.rbac.api.parameter;

import cn.bobdeng.rbac.domain.parameter.Parameter;
import cn.bobdeng.rbac.domain.parameter.ParameterDescription;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SetParameterForm {
    private String key;
    private String value;

    public ParameterDescription toDescription() {
        return new ParameterDescription(value, key);
    }

    public Parameter toEntity() {
        return new Parameter(key,new ParameterDescription(value,key));
    }
}
