package com.f.bilibili.domain.auth;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限控制-页面访问表
 * @TableName t_auth_menu
 */
@Data
public class AuthMenu implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 菜单项目名称
     */
    private String name;

    /**
     * 唯一编码
     */
    private String code;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}