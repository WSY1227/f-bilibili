package com.f.bilibili.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户动态表
 * @TableName t_user_moments
 */
@Data
public class UserMoments implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 动态类型：0视频 1直播 2专栏动态
     */
    private String type;

    /**
     * 内容详情id
     */
    private Long contentId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}