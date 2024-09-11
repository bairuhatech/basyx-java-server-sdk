package org.eclipse.digitaltwin.basyx.aasrepository.http;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<BasicAuthFilter> loggingFilter() {
        FilterRegistrationBean<BasicAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new BasicAuthFilter());
        registrationBean.addUrlPatterns("/shells/*"); // Apply to all the AAS-related API endpoints

        return registrationBean;
    }
}
