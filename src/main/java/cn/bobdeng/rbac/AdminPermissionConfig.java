package cn.bobdeng.rbac;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class AdminPermissionConfig {
    @Bean
    public FilterRegistrationBean<Filter> loginFilterRegistration() {
        AdminPermissionCheckFilter loginFilter = new AdminPermissionCheckFilter();
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(loginFilter);
        registration.addUrlPatterns("/rbac/admin/console/*");
        registration.setName("adminFilter");
        registration.setOrder(1);
        return registration;
    }
}
