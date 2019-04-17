package com.lzcloud.auth.dao;


import com.lzcloud.common.entity.Role;

import java.util.List;

/**
 * 〈角色Dao〉
 *
 * @author lzheng
 * @create 2019/04/13
 * @since 1.0.0
 */
public interface RoleDao {

    /**
     * 根据用户id查找角色列表
     * @param memberId 用户id
     * @return 角色列表
     */
    List<Role> findByMemberId(Integer memberId);
}
