package com.f.bilibili.domain.auth;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限控制--角色页面菜单关联表
 *
 * @TableName t_auth_role_menu
 */
@Data
public class AuthRoleMenu implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 页面菜单id
     */
    private Long menuId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     *冗余页面访问权限表
     */
    private AuthMenu authMenu;
}