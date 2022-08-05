package cn.bobdeng.rbac.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class BaseTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    protected void clearTable(String tableName) {
        jdbcTemplate.execute("truncate table " + tableName);
    }

}
