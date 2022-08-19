package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.ClearTable;
import cn.bobdeng.rbac.domain.rbac.*;
import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.domain.function.Function;
import cn.bobdeng.rbac.domain.function.FunctionRepository;
import cn.bobdeng.rbac.domain.function.Functions;
import cn.bobdeng.rbac.server.dao.ParameterDAO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserWithTenantFixture {
    @Autowired
    TenantRepository tenantRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DomainRepository domainRepository;
    @Autowired
    FunctionRepository functionRepository;
    @Autowired
    ClearTable clearTable;
    private Tenant tenant;
    private User user;

    public void init() {
        clearTable.clearTable("t_rbac_parameter");
        clearTable.clearTable("t_rbac_tenant");
        clearTable.clearTable("t_rbac_user");
        clearTable.clearTable("t_rbac_domain");
        clearTable.clearTable("t_rbac_password");
        clearTable.clearTable("t_rbac_login_name");
        clearTable.clearTable("t_rbac_role");
        clearTable.clearTable("t_rbac_user_role");
        tenant = tenantRepository.save(new Tenant(new TenantDescription("租户1")));
        domainRepository.save(new Domain(new DomainDescription("localhost", tenant.identity())));
        domainRepository.save(new Domain(new DomainDescription("host.docker.internal", tenant.identity())));

        user = getRbac().addUser(new UserDescription("张三"));
        user.savePassword(new RawPassword("123456"));
        getRbac().addLoginName(new LoginNameDescription("bobdeng", user.identity()));
        Role role = getRbac().newRole(new RoleDescription("角色1", getAllFunctions()));
        user.setRoles(Collections.singletonList(role));
    }

    public RbacContext.Rbac getRbac() {
        return tenantRepository.rbacContext().asRbac(tenant);
    }

    @NotNull
    private List<String> getAllFunctions() {
        List<String> allows = new ArrayList<>();
        List<Function> functions = new Functions(functionRepository).functions();
        readAllFunctions(functions, allows);
        return allows;
    }

    private void readAllFunctions(List<Function> functions, List<String> allows) {
        functions.forEach(function -> {
            allows.add(function.getKey());
            if (function.getDescription().getChildren() != null) {
                readAllFunctions(function.getDescription().getChildren(), allows);
            }
        });
    }

    public User user() {
        return user;
    }

    public Tenant getTenant() {
        return tenant;
    }
}
