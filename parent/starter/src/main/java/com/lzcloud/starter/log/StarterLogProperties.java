package com.lzcloud.starter.log;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @BelongsProject: house-parent-common
 * @BelongsPackage: com.eju.houseparent.starter.log
 * @Author: lzheng
 * @DATE: 2018/12/3 15:32
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "lzcloud.starter.syslog")
public class StarterLogProperties {

    String module;
    String aopClazz;
    String tableName;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getAopClazz() {
        return aopClazz;
    }

    public void setAopClazz(String aopClazz) {
        this.aopClazz = aopClazz;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
