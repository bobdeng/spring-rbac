package cn.bobdeng.rbac.api.parameter;

import cn.bobdeng.rbac.domain.Tenant;
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

    @GetMapping("/parameters")
    @Transactional
    public List<Parameter> parameters(@RequestAttribute("tenant") Tenant tenant) {
        return configurationContext.asConfigurer(tenant).listParameters();
    }

    @PutMapping("/parameters")
    @Transactional
    @Permission(allows = {"parameters"})
    public void setParameters(@RequestBody List<SetParameterForm> form,
                              @RequestAttribute("tenant") Tenant tenant) {
        List<Parameter> parameters = form.stream().map(SetParameterForm::toEntity).toList();
        configurationContext.asConfigurer(tenant).saveParameters(parameters);
    }

    @GetMapping("/parameters/{key}")
    @Transactional
    public String getParameter(@RequestAttribute("tenant") Tenant tenant, @PathVariable String key) {
        return configurationContext.asConfigurer(tenant).parameters().findByIdentity(key)
                .map(Parameter::description)
                .map(ParameterDescription::getValue)
                .orElse(null);
    }
}
