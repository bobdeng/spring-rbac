package cn.bobdeng.rbac.utils;

import cn.bobdeng.rbac.domain.Tenant;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

import java.util.Set;

public class SQLInterceptor {
    private String sql;
    private static final Set<String> TABLE_HAS_NO_TENANT_ID = Set.of("t_rbac_tenant",
            "t_rbac_domain",
            "t_rbac_password",
            "t_rbac_user_role");

    public SQLInterceptor(String sql) {
        this.sql = sql;
    }

    public String intercept(Tenant tenant, Set<String> tablesIgnored) {
        Statement stmt;
        try {
            stmt = CCJSqlParserUtil.parse(this.sql);
        } catch (JSQLParserException e) {
            return sql;
        }
        Select select = (Select) stmt;
        PlainSelect selectBody = (PlainSelect) select.getSelectBody();
        FromItem fromItem = selectBody.getFromItem();
        String tableName = fromItem.toString().substring(0, fromItem.toString().indexOf(' ')).trim();
        if (TABLE_HAS_NO_TENANT_ID.contains(tableName) || tablesIgnored.contains(tableName)) {
            return sql;
        }
        addTenantConditionIfHasSelect(tenant, selectBody, fromItem.getAlias().toString().trim());
        return select.getSelectBody().toString();
    }

    private void addTenantConditionIfHasSelect(Tenant tenant, PlainSelect selectBody, String table) {
        EqualsTo tenantCondition = new EqualsTo(new Column(table + ".tenant_id"), new LongValue(tenant.identity()));
        if (selectBody.getWhere() != null) {
            selectBody.setWhere(new AndExpression(tenantCondition, selectBody.getWhere()));
            return;
        }
        selectBody.setWhere(tenantCondition);
    }

}
