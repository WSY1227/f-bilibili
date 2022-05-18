package com.f.bilibili.dao;

import com.f.bilibili.domain.auth.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 16692
 * @description 针对表【t_user_role(用户角色关联表)】的数据库操作Mapper
 * @createDate 2022-05-16 19:55:41
 * @Entity com.f.bilibili.domain.auth.UserRole
 */
@Mapper
public interface UserRoleDao {
    /**
     * 通过用户id获取用户角色信息
     */
    List<UserRole> getUserRoleByUserId(Long userId);
}




