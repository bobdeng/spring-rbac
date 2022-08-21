package cn.bobdeng.rbac;

import cn.bobdeng.rbac.domain.DomainRepository;
import cn.bobdeng.rbac.domain.TenantRepository;
import cn.bobdeng.rbac.security.SessionStore;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class RbacWebConfig {
    @Bean
    public FilterRegistrationBean<Filter> loginFilterRegistration(SessionStore sessionStore,
                                                                  DomainRepository domainRepository,
                                                                  TenantRepository tenantRepository) {
        SessionCheckFilter loginFilter = new SessionCheckFilter(sessionStore, domainRepository, tenantRepository);
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(loginFilter);
        registration.addUrlPatterns("/*");
        registration.setName("sessionFilter");
        registration.setOrder(1);
        return registration;
    }
}
