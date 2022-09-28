package cn.bobdeng.rbac.utils;

import cn.bobdeng.rbac.domain.Tenant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SQLInterceptorTest {
    TableHasNoTenantId tableHasNoTenantId;

    @BeforeEach
    public void setup() {
        tableHasNoTenantId = () -> Set.of("a");
    }

    @Test
    public void 当没有Where子句但是有Order等子句的时候() {
        SQLInterceptor sqlInterceptor = new SQLInterceptor("select promotiond0_.id, promotiond0_.tenant_id from t_promotion promotiond0_ order by promotiond0_.id");
        String result = sqlInterceptor.intercept(new Tenant(10, null), tableHasNoTenantId.tables());
        assertEquals("SELECT promotiond0_.id, promotiond0_.tenant_id FROM t_promotion promotiond0_ WHERE promotiond0_.tenant_id = 10 ORDER BY promotiond0_.id", result);
    }

    @Test
    public void 当有Where子句但是也有Order等子句的时候() {
        SQLInterceptor sqlInterceptor = new SQLInterceptor("select promotiond0_.id, promotiond0_.tenant_id from t_promotion promotiond0_ WHERE promotiond0_.id=1 order by promotiond0_.id");
        String result = sqlInterceptor.intercept(new Tenant(10, null), tableHasNoTenantId.tables());
        assertEquals("SELECT promotiond0_.id, promotiond0_.tenant_id FROM t_promotion promotiond0_ WHERE promotiond0_.tenant_id = 10 AND promotiond0_.id = 1 ORDER BY promotiond0_.id", result);
    }

    @Test
    public void selectCount增加租户ID() {
        SQLInterceptor sqlInterceptor = new SQLInterceptor("SELECT count(promotiond0_.id) AS col_0_0_ FROM t_promotion promotiond0_");
        String result = sqlInterceptor.intercept(new Tenant(10, null), tableHasNoTenantId.tables());
        assertEquals("SELECT count(promotiond0_.id) AS col_0_0_ FROM t_promotion promotiond0_ WHERE promotiond0_.tenant_id = 10", result);
    }


    @Test
    public void 没有租户ID的表不操作() {
        SQLInterceptor sqlInterceptor = new SQLInterceptor("SELECT count(promotiond0_.id) AS col_0_0_ FROM t_rbac_tenant promotiond0_");
        String result = sqlInterceptor.intercept(new Tenant(10, null), tableHasNoTenantId.tables());
        assertEquals("SELECT count(promotiond0_.id) AS col_0_0_ FROM t_rbac_tenant promotiond0_", result);
    }


    @Test
    public void bad_sql() {
        SQLInterceptor sqlInterceptor = new SQLInterceptor("SELEC count(promotiond0_.id) AS col_0_0_ FROM t_rbac_tenant promotiond0_");
        String result = sqlInterceptor.intercept(new Tenant(10, null), tableHasNoTenantId.tables());
        assertEquals("SELEC count(promotiond0_.id) AS col_0_0_ FROM t_rbac_tenant promotiond0_", result);
    }
}