package com.f.bilibili.dao;

import com.f.bilibili.domain.auth.AuthRoleElementOperation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author 16692
 * @description 针对表【t_auth_role_element_operation(权限控制--角色与元素操作关联表)】的数据库操作Mapper
 * @createDate 2022-05-16 19:55:41
 * @Entity com.f.bilibili.domain.auth.AuthRoleElementOperation
 */
@Mapper
public interface AuthRoleElementOperationDao {
    /**
     * 通过角色id集合获取操作权限列表
     *
     * @param roleIdSet 角色id集合
     * @return 操作权限列表
     */
    List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(@Param("roleIdSet") Set<Long> roleIdSet);
}




