package cn.bobdeng.rbac;

import org.junit.Rule;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Rule
    public GenericContainer redis = new GenericContainer("redis:6.2.4")
            .withExposedPorts(6379);

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

    @Bean
    RedissonClient redisson() {
        redis.start();
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        config.useSingleServer().setAddress("redis://" + redis.getHost() + ":" + redis.getMappedPort(6379));
        config.setThreads(1);
        config.setNettyThreads(1);
        return Redisson.create(config);
    }


}
