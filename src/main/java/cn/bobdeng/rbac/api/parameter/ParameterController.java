package cn.bobdeng.rbac.api.parameter;

import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.domain.config.ConfigurationContext;
import cn.bobdeng.rbac.domain.config.Parameter;
import cn.bobdeng.rbac.domain.config.ParameterDescription;
import cn.bobdeng.rbac.security.Permission;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class ParameterController {
    private final TenantRepository tenantRepository;
    private final ConfigurationContext configurationContext;

    public ParameterController(TenantRepository tenantRepository, ConfigurationContext configurationContext) {
        this.tenantRepository = tenantRepository;
        this.configurationContext = configurationContext;
    }

    @GetMapping("/api/1.0/parameters")
    @Transactional
    public List<Parameter> parameters() {
        return configurationContext.configurer().listParameters();
    }

    @PutMapping("/api/1.0/parameters")
    @Transactional
    @Permission(allows = {"parameters"})
    public void setParameters(@RequestBody List<SetParameterForm> form) {
        List<Parameter> parameters = form.stream().map(SetParameterForm::toEntity).toList();
        configurationContext.configurer().saveParameters(parameters);
    }

    @GetMapping("/api/1.0/parameters/{key}")
    @Transactional
    public String getParameter(@PathVariable String key) {
        return configurationContext.configurer().getParameterByName(key)
                .map(Parameter::description)
                .map(ParameterDescription::getValue)
                .orElse(null);
    }
}
