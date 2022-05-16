package com.f.bilibili.dao;

import com.f.bilibili.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author 16692
 * @description 针对表【t_user(用户表)】的数据库操作Mapper
 * @createDate 2022-04-26 17:37:35
 * @Entity com.f.bilibili.domain.User
 */
@Mapper
public interface UserDao {
    /**
     * <h2>通过手机号查找用户</h2>
     */
    User getUserByPhone(String phone);

    /**
     * <h2>添加用户</h2>
     */
    Integer addUser(User user);

    /**
     * <h2>通过id查找用户</h2>
     */
    User getUserById(Long userId);

    /**
     * 修改用户账号信息
     */
    Integer updateUsers(User user);



}




