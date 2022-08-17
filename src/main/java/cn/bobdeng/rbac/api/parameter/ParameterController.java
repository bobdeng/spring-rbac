package cn.bobdeng.rbac.api.parameter;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.parameter.Parameter;
import cn.bobdeng.rbac.domain.parameter.ParameterDescription;
import cn.bobdeng.rbac.security.Permission;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class ParameterController {
    @GetMapping("/parameters")
    @Transactional
    public List<Parameter> parameters(@RequestAttribute("tenant") Tenant tenant) {
        return tenant.parameters().list().toList();
    }

    @PutMapping("/parameters")
    @Transactional
    @Permission(allows = {"parameters"})
    public void setParameters(@RequestBody List<SetParameterForm> form,
                              @RequestAttribute("tenant") Tenant tenant) {
        List<ParameterDescription> parameterDescriptions = form.stream().map(SetParameterForm::toDescription).toList();
        tenant.saveParameters(parameterDescriptions);
    }
}
