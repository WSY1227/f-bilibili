package com.f.bilibili.service;

import com.f.bilibili.dao.UserFollowingDao;
import com.f.bilibili.domain.FollowingGroup;
import com.f.bilibili.domain.User;
import com.f.bilibili.domain.UserFollowing;
import com.f.bilibili.domain.UserInfo;
import com.f.bilibili.domain.constant.UserConstant;
import com.f.bilibili.domain.exception.ConditionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName: UserFollowingService
 * @Description: <h1>用户关注服务层</h1>
 * @author: XU
 * @date: 2022年04月28日 18:02
 **/
@Service
public class UserFollowingService {
    @Autowired
    private UserFollowingDao userFollowingDao;
    @Autowired
    private FollowingGroupService followingGroupService;
    @Autowired
    private UserService userService;

    /**
     * <h2>关注用户</h2>
     *
     * @param userFollowing 用户关注信息
     */
    @Transactional//开启事务避免删除了没有新增
    public void addUserFollowing(UserFollowing userFollowing) {
        /**获取分组的id*/
        Long groupId = userFollowing.getGroupId();
        if (groupId == null) {
            /**如果传入的分组id为空读取默认分组信息*/
            FollowingGroup followingGroup = followingGroupService.getByType(UserConstant.USER_FOLLOWING_GROUP_TYPE_DEFAULT);
            /**添加分组id，对用户关注进行分组*/

            userFollowing.setGroupId(followingGroup.getId());
        } else {
            /**传入了指定分组，通过分组的id拿到分组信息*/
            FollowingGroup followingGroup = followingGroupService.getById(groupId);
            /**判断分组是否存在*/
            if (followingGroup == null) {
                throw new ConditionException("选择的分组不存在");
            }
        }
        /**获取用户关注的用户id*/
        Long followingId = userFollowing.getFollowingId();
        /**获取对应用户id账号信息*/
        User user = userService.getUserById(followingId);
        /**判断用户是否存在*/
        if (user == null) {
            throw new ConditionException("关注的用户不存在");
        }
        /**删除原本的关联关系*/
        userFollowingDao.deleteUserFollowing(userFollowing.getUserId(), followingId);
        /**添加创建时间*/
        userFollowing.setCreateTime(new Date());
        /**添加用户关注信息*/
        userFollowingDao.addUserFollowing(userFollowing);
    }

    /**
     * <h1>获取用户关注列表</h1>
     *
     * @param userId 传入用户id
     * @return 返回用户关注列表
     */
    public List<FollowingGroup> getUserFollowings(Long userId) {
        //第一步：获取被关注的用户列表
        //第二部：根据被关注用户的id查询关注用户的基本信息
        //第三步：将被关注用户按关注分组进行分类

        //第一步
        /**根据用户id获取被该用户关注的用户列表*/
        List<UserFollowing> list = userFollowingDao.getUserFollowings(userId);
        //第二步
        /**将list列表中每个被关注的用户id(followingId)拿到,并保存到followingIdSet集合*/
        Set<Long> followingIdSet = list.stream().map(UserFollowing::getFollowingId).collect(Collectors.toSet());
        /**创建存用户信息的列表*/
        List<UserInfo> userInfoList = new ArrayList<>();
        /**判断获取到的集合是否为空*/
        if (followingIdSet.size() > 0) {
            userInfoList = userService.getUserInfoByUserIds(followingIdSet);
        }
        /**将被关注的用户的信息一一对应的写入到list中*/
        for (UserFollowing userFollowing : list) {//遍历被关注信息表
            for (UserInfo userInfo : userInfoList) {//便利用户信息表
                if (userFollowing.getFollowingId().equals(userInfo.getUserId())) {//找到被关注的用户信息，并添加
                    userFollowing.setUserInfo(userInfo);
                }
            }
        }
        //第三步
        /**获取用户的分组信息*/
        List<FollowingGroup> groupList = followingGroupService.getFollowingGroupByUserId(userId);
        /**创建一个新的分组，存放所有的信息（就是不分组的）*/
        FollowingGroup allGroup = new FollowingGroup();
        /**设置默认名称*/
        allGroup.setName(UserConstant.USER_FOLLOWING_GROUP_ALL_NAME);
        /**将用户信息列表直接存入allGroup*/
        allGroup.setFollowingUserInfoList(userInfoList);
        /**创建存放所有分组信息的列表*/
        List<FollowingGroup> result = new ArrayList<>();
        /**将创建的全部分组加入*/
        result.add(allGroup);
        for (FollowingGroup followingGroup : groupList) {/**第一层循环读取用户所有分组信息*/
            /**存放相同分组的被关注用户的信息*/
            List<UserInfo> infoList = new ArrayList<>();
            for (UserFollowing userFollowing : list) {/**第二层遍历用户关注的用户表*/
                /**匹配分组信息*/
                if (followingGroup.getId().equals(userFollowing.getGroupId())) {
                    /**将分组id匹配到的被关注用户放到对应分组中*/
                    infoList.add(userFollowing.getUserInfo());
                }
            }
            /**将当前分组的户信息进行存入*/
            followingGroup.setFollowingUserInfoList(infoList);
            /**存放当前分组信息*/
            result.add(followingGroup);
        }
        /**返回所有分组信息*/
        return result;
    }

    //第一步:获取当前用户的粉丝列表
    //第二步:根据粉丝的用户id查询基本信息
    //第三步:查询当前用户是否已经关注该粉丝
    public List<UserFollowing> getUserFans(Long userId) {
        //第一步
        /**获取当前用户粉丝列表*/
        List<UserFollowing> fanList = userFollowingDao.getUserFans(userId);

        //第二步
        /**获得粉丝的userId集合*/
        Set<Long> fanIdSet = fanList.stream().map(UserFollowing::getUserId).collect(Collectors.toSet());
        /**粉丝信息列表创建*/
        List<UserInfo> userInfoList = new ArrayList<>();
        if (fanIdSet.size() > 0) {//当粉丝不为空
            /**获取粉丝信息列表*/
            userInfoList = userService.getUserInfoByUserIds(fanIdSet);
        }
        //第三步
        /**拿到当前用户的关注用户，用来判断是否互粉*/
        List<UserFollowing> userFollowingList = userFollowingDao.getUserFollowings(userId);
        /**添加粉丝信息*/
        for (UserFollowing fan : fanList) {/**第一层循环粉丝列表*/
            for (UserInfo userInfo : userInfoList) {/**第二层循环粉丝信息*/
                if (fan.getUserId().equals(userInfo.getUserId())) {/**如果粉丝列表的用户id*/
                    /**添加是否互粉默认值*/
                    userInfo.setFollowed(false);
                    /**添加调用用户信息*/
                    fan.setUserInfo(userInfo);
                }
            }

            /**循环关注用户列表，对比粉丝列表，判断是否互粉*/
            for (UserFollowing userFollowing : userFollowingList) {
                if (userFollowing.getFollowingId().equals(fan.getUserId())) {
                    /**如果粉丝列表的用户id与当前用户关注的用户id匹配到，则是互粉*/
                    fan.getUserInfo().setFollowed(true);
                }
            }
        }
        return fanList;
    }

    /**
     * <h2>创建新的分组</h2>
     *
     * @param followingGroup 新的分组信息
     * @return 返回新的分组id
     */
    public Long addUserFollowingGroups(FollowingGroup followingGroup) {
        /**添加分组创建时间*/
        followingGroup.setCreateTime(new Date());
        /**添加分组属性*/
        followingGroup.setType(UserConstant.USER_FOLLOWING_GROUP_TYPE_USER);
        /**添加分组*/
        followingGroupService.addFollowingGroup(followingGroup);
        /**返回新建的分组id*/
        return followingGroup.getId();
    }

    /**
     * <h2>根据id返回分组信息</h2>
     *
     * @param userId 需要查询的用户id
     * @return 返回该用户的分组信息
     */
    public List<FollowingGroup> getUserFollowingGroups(Long userId) {
        return followingGroupService.getUserFollowingGroups(userId);
    }

    /**
     * <h2>修改互粉状态</h2>
     *
     * @param userInfoList 粉丝列表
     * @param userId       当前用户id
     * @return 返回修改了互粉状态的粉丝列表
     */
    public List<UserInfo> checkFollowingStatus(List<UserInfo> userInfoList, Long userId) {
        /**根据当前用户id获取该用户关注的列表*/
        List<UserFollowing> userFollowingList = userFollowingDao.getUserFollowings(userId);
        /**循环粉丝列表*/
        for (UserInfo userInfo : userInfoList) {
            /**先设置未互粉*/
            userInfo.setFollowed(false);
            for (UserFollowing userFollowing : userFollowingList) {
                /**查询该用户的关注列表是否含有粉丝id,有就是互粉，设置为ture*/
                if (userFollowing.getFollowingId().equals(userInfo.getUserId())) {
                    userInfo.setFollowed(true);
                }
            }
        }
        /**返回修改后的粉丝信息列表*/
        return userInfoList;
    }
}
