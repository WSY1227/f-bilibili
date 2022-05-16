package com.f.bilibili.domain.auth;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色关联表
 * @TableName t_user_role
 */
@Data
public class UserRole implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 创建时间
     */
    private Date createTime;

}