package com.f.bilibili.service;

import com.f.bilibili.dao.FollowingGroupDao;
import com.f.bilibili.domain.FollowingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: FollowingGroupService
 * @Description: 关注分组服务
 * @author: XU
 * @date: 2022年04月28日 18:05
 **/
@Service
public class FollowingGroupService {
    @Autowired
    private FollowingGroupDao followingGroupDao;

    /**
     * 根据id获取分组信息
     */
    public FollowingGroup getById(Long id) {
        return followingGroupDao.getById(id);
    }

    /**
     * 根据type获取分组信息
     */
    public FollowingGroup getByType(String type) {
        return followingGroupDao.getByType(type);
    }

    /**
     * 通过用户id获取分组列表
     */
    public List<FollowingGroup> getFollowingGroupByUserId(Long userId) {
        return followingGroupDao.getFollowingGroupByUserId(userId);
    }

    /**
     * 添加分组
     */
    public void addFollowingGroup(FollowingGroup followingGroup) {
        followingGroupDao.addFollowingGroup(followingGroup);
    }

    /**
     * 根据用户id返回关注分组
     */
    public List<FollowingGroup> getUserFollowingGroups(Long userId) {
        return followingGroupDao.getUserFollowingGroups(userId);
    }
}
