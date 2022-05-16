package com.f.bilibili.dao;

import com.f.bilibili.domain.FollowingGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 16692
 * @description 针对表【t_following_group(用户关注分组表)】的数据库操作Mapper
 * @createDate 2022-04-28 17:38:08
 * @Entity com.f.bilibili.domain.FollowingGroup
 */
@Mapper
public interface FollowingGroupDao {
    /**
     * 根据id获取分组
     */
    FollowingGroup getById(Long id);

    /**
     * 根据属性获取分组
     */
    FollowingGroup getByType(String type);

    /**
     * 根据用户id返回分组列表
     */
    List<FollowingGroup> getFollowingGroupByUserId(Long userId);

    /**
     * 添加新分组
     */
    Integer addFollowingGroup(FollowingGroup followingGroup);

    /**
     * 根据用户id返回该用户的关注分组
     */
    List<FollowingGroup> getUserFollowingGroups(Long userId);
}




