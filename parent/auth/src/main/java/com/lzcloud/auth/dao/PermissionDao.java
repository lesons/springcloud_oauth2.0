package com.lzcloud.auth.dao;

import com.lzcloud.common.entity.Permission;

import java.util.List;

/**
 * 〈权限Dao〉
 *
 * @author lzheng
 * @create 2019/04/13
 * @since 1.0.0
 */
public interface PermissionDao {

    /**
     * 根据角色id查找权限列表
     * @param roleId 角色id
     * @return 权限列表
     */
    List<Permission> findByRoleId(Integer roleId);
}
