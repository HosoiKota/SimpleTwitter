package com.example.xJava8.config;

import com.example.xJava8.filter.AccountRestrictionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AccountRestrictionFilter> restrictionFilter() {
        FilterRegistrationBean<AccountRestrictionFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AccountRestrictionFilter());
        registrationBean.addUrlPatterns("/tweet", "/comment");

        return registrationBean;
    }
}
