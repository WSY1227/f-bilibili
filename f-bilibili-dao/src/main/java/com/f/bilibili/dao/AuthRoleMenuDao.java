package com.f.bilibili.dao;

import com.f.bilibili.domain.auth.AuthRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author 16692
 * @description 针对表【t_auth_role_menu(权限控制--角色页面菜单关联表)】的数据库操作Mapper
 * @createDate 2022-05-16 19:55:41
 * @Entity com.f.bilibili.domain.auth.AuthRoleMenu
 */
@Mapper
public interface AuthRoleMenuDao {
    /**
     * 通过角色id集合获取页面权限列表
     */
    List<AuthRoleMenu> getRoleMenuList(@Param("roleIdSet") Set<Long> roleIdSet);
}




