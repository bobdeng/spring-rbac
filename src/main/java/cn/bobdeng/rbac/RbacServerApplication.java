package cn.bobdeng.rbac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class RbacServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacServerApplication.class, args);
    }

}
