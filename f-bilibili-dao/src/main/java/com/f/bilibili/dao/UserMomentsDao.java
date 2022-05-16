package com.f.bilibili.dao;

import com.f.bilibili.domain.UserMoments;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 16692
 * @description 针对表【t_user_moments(用户动态表)】的数据库操作Mapper
 * @createDate 2022-05-06 13:40:46
 * @Entity com.f.bilibili.domain.UserMoments
 */
@Mapper
public interface UserMomentsDao {

    Integer addUserMoments(UserMoments userMoments);
}




