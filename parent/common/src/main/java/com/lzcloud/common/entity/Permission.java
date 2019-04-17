package com.lzcloud.common.entity;

import lombok.Data;

import java.util.Date;

/**
 * 〈权限实体〉
 *
 * @author lzheng
 * @create 2019/04/13
 * @since 1.0.0
 */
@Data
public class Permission {

    private int id;
    private String zuulPrefix;
    private String servicePrefix;
    private String method;
    private String uri;
    private Date createTime;
    private Date updateTime;
}
