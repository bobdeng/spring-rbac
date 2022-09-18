package cn.bobdeng.rbac;

import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Service
public class RbacDBMigrate {
    private Flyway flyway;
    private final DataSource dataSource;

    public RbacDBMigrate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void init() {
        configFlyway();
        migration();
    }

    public void configFlyway() {
        flyway = Flyway.configure()
                .locations("classpath:rbac/db/migrations")
                .dataSource(dataSource)
                .baselineOnMigrate(true)
                .load();
    }

    public void migration() {
        flyway.migrate();
    }
}
