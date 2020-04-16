package com.lzcloud.auth.config.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.servlet.http.HttpServletResponse;

/**
 * 〈资源认证服务器〉
 *
 * @author lzheng
 * @create 2019/04/13
 * @since 1.0.0
 */
@Configuration
@EnableResourceServer
@EnableOAuth2Client
@Order(3)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(applicationName);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
            .and()
            .requestMatchers().antMatchers("/api/**")
            .and()
            .authorizeRequests()
            .antMatchers("/api/**").authenticated()
            .and()
            .httpBasic();
    }
}
