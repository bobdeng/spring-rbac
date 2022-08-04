package cn.bobdeng.rbac;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

public class Cookies {
    public static final String AUTHORIZATION = "Authorization";
    public static final String ADMIN_AUTHORIZATION = "AdminAuthorization";

    @Configuration
    public static class DataSourceConfig {

        @Bean
        public DataSource dataSource() {
            DockerImageName dockerImageName = DockerImageName.parse("mysql:8.0.30");
            MySQLContainer mySQLContainer = new MySQLContainer(dockerImageName);
            mySQLContainer.withReuse(true).start();
            return DataSourceBuilder.create()
                    .driverClassName(mySQLContainer.getDriverClassName())
                    .username(mySQLContainer.getUsername())
                    .password(mySQLContainer.getPassword())
                    .url(mySQLContainer.getJdbcUrl() + "?characterEncoding=utf-8")
                    .build();
        }


    }
}
