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

}
