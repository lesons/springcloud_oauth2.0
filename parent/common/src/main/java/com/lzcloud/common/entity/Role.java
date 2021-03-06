package com.lzcloud.common.entity;

import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * 〈角色实体〉
 *
 * @author lzheng
 * @create 2019/04/13
 * @since 1.0.0
 */
@Data
public class Role {

    private int id;
    private String roleName;
    private short valid;
    private Date createTime;
    private Date updateTime;
    private Set<Permission> permissions;
}
