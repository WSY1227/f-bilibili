package com.f.bilibili.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户关注表
 *
 * @TableName t_user_following
 */
@Data
public class UserFollowing implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 被关注用户的id
     */
    private Long followingId;

    /**
     * 关注分组id
     */
    private Long groupId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 接收用户信息
     */
    private UserInfo userInfo;

}