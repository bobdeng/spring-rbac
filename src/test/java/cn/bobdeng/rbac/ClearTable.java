package cn.bobdeng.rbac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ClearTable {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private final Set<String> AutoIncrementTables = Stream.of("t_rbac_tenant", "t_rbac_user", "t_rbac_login_name"
            , "t_rbac_password", "t_rbac_domain", "t_rbac_role", "t_rbac_user_role", "t_rbac_organization").collect(Collectors.toSet());

    public void clearTable(String name) {
        jdbcTemplate.execute("truncate table " + name);
        try {
            if (AutoIncrementTables.contains(name)) {
                jdbcTemplate.execute("alter table " + name + " AUTO_INCREMENT=" + new Random().nextInt(100000));
            }
        } catch (Exception e) {
        }
    }
}
