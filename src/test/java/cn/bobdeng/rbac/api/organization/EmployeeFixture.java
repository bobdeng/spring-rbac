package cn.bobdeng.rbac.api.organization;

import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.server.dao.EmployeeDAO;
import cn.bobdeng.rbac.server.dao.EmployeeDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeFixture {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    EmployeeDAO employeeDAO;

    public void clear() {
        jdbcTemplate.execute("truncate table t_rbac_employee");
    }

    public void set(Integer organizationDOId, User user) {
        employeeDAO.save(EmployeeDO.builder()
                .id(user.identity())
                .organizationId(organizationDOId)
                .build());
    }
}
