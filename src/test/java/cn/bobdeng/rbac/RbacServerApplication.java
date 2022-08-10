package cn.bobdeng.rbac;

import cn.bobdeng.rbac.server.NotifierImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@SpringBootApplication
@EnableJpaRepositories
public class RbacServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RbacServerApplication.class, args);
    }

    @Bean
    NotifierImpl notifier() {
        return new NotifierImpl();
    }

}
