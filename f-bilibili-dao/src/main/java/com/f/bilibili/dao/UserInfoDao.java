package com.f.bilibili.dao;

import com.f.bilibili.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 16692
 * @description 针对表【t_user_info(用户基本信息表)】的数据库操作Mapper
 * @createDate 2022-04-26 17:37:35
 * @Entity com.f.bilibili.domain.UserInfo
 */
@Mapper
public interface UserInfoDao {
    /**
     * 添加用户信息
     */
    Integer addUserInfo(UserInfo userInfo);

    /**
     * <h2>通过id查找用户信息</h2>
     */
    UserInfo getUserInfoUserId(Long id);

    /**
     * <h2>修改用户信息</h2>
     */
    Integer updateUserInfo(UserInfo userInfo);

    /**
     * 通过被用户的id集合返回每个用户的信息
     */
    List<UserInfo> getUserInfoByUserIds(Set<Long> userIdList);

    /**
     * 查询符合查询条件的总数量
     */
    Integer pageCountUserInfos(Map<String, Object> params);

    /**
     * 根据分页条件返回需求页的用户信息
     */
    List<UserInfo> pageListUserInfos(Map<String, Object> params);
}




