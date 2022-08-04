package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.TenantDescription;
import cn.bobdeng.rbac.server.dao.TenantDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RbacServerApplicationTests {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    TenantDAO tenantDAO;

    @Test
    void contextLoads() {
        assertNotNull(jdbcTemplate);
    }

}
