package com.f.bilibili.dao;

import com.f.bilibili.domain.UserFollowing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 16692
 * @description 针对表【t_user_following(用户关注表)】的数据库操作Mapper
 * @createDate 2022-04-28 17:42:32
 * @Entity com.f.bilibili.domain.UserFollowing
 */
@Mapper
public interface UserFollowingDao {
    /**
     * 根据用户id和关注用户id删除对应的用户关注信息
     */
    Integer deleteUserFollowing(@Param("userId") Long userId, @Param("followingId") Long followingId);

    /**
     * 添加用户关注信息
     */
    Integer addUserFollowing(UserFollowing userFollowing);

    /**
     * 根据用户id获取用户关注的列表
     */
    List<UserFollowing> getUserFollowings(Long userId);

    /**
     * 获取用户粉丝列表
     */
    List<UserFollowing> getUserFans(Long followingId);

}




