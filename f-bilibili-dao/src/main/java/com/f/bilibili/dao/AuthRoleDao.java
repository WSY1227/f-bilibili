package com.f.bilibili.dao;

import com.f.bilibili.domain.auth.AuthRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 16692
 * @description 针对表【t_auth_role(权限控制--角色表)】的数据库操作Mapper
 * @createDate 2022-05-16 19:55:40
 * @Entity com.f.bilibili.domain.auth.AuthRole
 */
@Mapper
public interface AuthRoleDao {
    /**
     * 通过角色编码获取角色信息
     */
    AuthRole getRoleByCode(String code);
}




