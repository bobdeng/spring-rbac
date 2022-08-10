package cn.bobdeng.rbac.api.organization;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.server.dao.OrganizationDAO;
import cn.bobdeng.rbac.server.dao.OrganizationDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationFixture {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    OrganizationDAO organizationDAO;

    public void clear() {
        jdbcTemplate.execute("truncate table t_rbac_organization");
    }

    public OrganizationDO newOne(Tenant tenant) {
        return organizationDAO.save(OrganizationDO.builder()
                .name("总公司")
                .parentId(null)
                .tenantId(tenant.identity())
                .build());
    }

    public List<OrganizationDO> list(Tenant tenant) {
        return organizationDAO.findAllByTenantId(tenant.getId());
    }
}
