package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.domain.*;
import cn.bobdeng.rbac.domain.function.Function;
import cn.bobdeng.rbac.domain.function.FunctionRepository;
import cn.bobdeng.rbac.domain.function.Functions;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private Tenant tenant;
    private User user;

    public void init() {
        clearTable("t_rbac_tenant");
        clearTable("t_rbac_user");
        clearTable("t_rbac_domain");
        clearTable("t_rbac_password");
        clearTable("t_rbac_login_name");
        clearTable("t_rbac_role");
        tenant = tenantRepository.save(new Tenant(new TenantDescription("租户1")));
        domainRepository.save(new Domain(new DomainDescription("localhost", tenant.identity())));
        user = tenant.addUser(new UserDescription("张三"));
        user.savePassword(new RawPassword("123456"));
        tenant.addLoginName(new LoginNameDescription("bobdeng", user));
        Role role = tenant.newRole(new RoleDescription("角色1", getAllFunctions()));
        user.setRoles(Arrays.asList(role));
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

    protected void clearTable(String tableName) {
        jdbcTemplate.execute("truncate table " + tableName);
    }

    public User user() {
        return user;
    }
}
