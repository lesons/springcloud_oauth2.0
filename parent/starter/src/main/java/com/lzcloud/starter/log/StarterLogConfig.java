package com.lzcloud.starter.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @BelongsProject: house-parent-common
 * @BelongsPackage: com.eju.houseparent.starter.log
 * @Author: lzheng
 * @DATE: 2018/12/3 15:32
 * @Description:
 */
@Configuration
@EnableConfigurationProperties(StarterLogProperties.class)
@AutoConfigureAfter(DataSource.class)
@ComponentScan({"com.lzcloud.starter.log"})
public class StarterLogConfig {


    @Autowired
    DataSource dataSource;


    @Autowired
    StarterLogProperties starterLogProperties;

    @Bean
    @ConditionalOnMissingBean(UserInfoInterface.class)
    public UserInfoInterface getUserInfo() {
        return () -> null;
    }


    @Bean
    public SysLogService getSysLogService() {
        SysLogService service = new SysLogService(dataSource, starterLogProperties);
        return service;
    }
}
