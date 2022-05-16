package com.f.bilibili.domain.auth;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限控制--角色与元素操作关联表
 *
 * @TableName t_auth_role_element_operation
 */
@Data
public class AuthRoleElementOperation implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 元素操作id
     */
    private Long elementOperationId;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 冗余页面操作权限表
     */
    private AuthElementOperation authElementOperation;
}