package com.f.bilibili.service;

import com.f.bilibili.dao.UserRoleDao;
import com.f.bilibili.domain.auth.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: UserRoleService
 * @Description: 用户角色关联服务
 * @author: XU
 * @date: 2022年05月17日 12:28
 **/
@Service
public class UserRoleService {
    @Autowired
    private UserRoleDao userRoleDao;
    /**
     * 通过用户id获取用户角色列表
     *
     * @param userId 用户id
     * @return 角色列表
     */
    public List<UserRole> getUserRoleByUserId(Long userId) {
        return userRoleDao.getUserRoleByUserId(userId);
    }
}
