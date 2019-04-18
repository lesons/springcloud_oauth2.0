package com.lzcloud.starter.log;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author lz
 * @since 2018-10-29
 */
@Data
@Slf4j
public class SysUser {

    /**
     * 用户id
     */
    private Long id;
    private Long userId;
    private String userMobile;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
