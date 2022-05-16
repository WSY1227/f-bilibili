package com.f.bilibili.api;

import com.f.bilibili.api.support.UserSupport;
import com.f.bilibili.domain.FollowingGroup;
import com.f.bilibili.domain.JsonResponse;
import com.f.bilibili.domain.UserFollowing;
import com.f.bilibili.service.UserFollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: UserFollowingApi
 * @Description: 用户关注api
 * @author: XU
 * @date: 2022年05月02日 19:31
 **/
@RestController
public class UserFollowingApi {
    @Autowired
    private UserFollowingService userFollowingService;

    @Autowired
    private UserSupport userSupport;

    /**
     * <h2>关注用户</h2>
     *
     * @param userFollowing 用户关注信息
     * @return 成功则返回成功信息
     */
    @PostMapping("/user-followings")
    public JsonResponse<String> addUserFollowings(@RequestBody UserFollowing userFollowing) {
        /**获取当前用户id*/
        Long userId = userSupport.getCurrentUserId();
        /**添加当前用户id*/
        userFollowing.setUserId(userId);
        /**调用添加关注服务*/
        userFollowingService.addUserFollowing(userFollowing);
        /**运行到这说明成功，返回成功信息*/
        return JsonResponse.success();
    }

    /**
     * <h1>查询当前用户的分组列表</h1>
     *
     * @return 返回用户分组列表
     */
    @GetMapping("/user-followings")
    public JsonResponse<List<FollowingGroup>> getUserFollowings() {
        /**获取当前用户id*/
        Long userId = userSupport.getCurrentUserId();
        /**根据当前用户id查询用户分组列表*/
        List<FollowingGroup> result = userFollowingService.getUserFollowings(userId);
        /**返回用户分组列表*/
        return new JsonResponse<>(result);
    }

    /**
     * <h1>获取当前用户粉丝列表</h1>
     *
     * @return 返回当前用户粉丝列表
     */
    @GetMapping("/user-fans")
    public JsonResponse<List<UserFollowing>> getUserFans() {
        /**获取当前用户id*/
        Long userId = userSupport.getCurrentUserId();
        /**根据当前用户id获取当前用户粉丝列表*/
        List<UserFollowing> userFans = userFollowingService.getUserFans(userId);
        /**返回用户粉丝列表*/
        return new JsonResponse<>(userFans);
    }

    /**
     * <h2>创建新的分组并返回分组id</h2>
     *
     * @param followingGroup 新的分组信息
     * @return 返回分组信息
     */
    @PostMapping("/user-following-groups")
    public JsonResponse<Long> addUserFollowingGroups(@RequestBody FollowingGroup followingGroup) {
        /**获取当前用户id*/
        Long userId = userSupport.getCurrentUserId();
        /**添加当前用户id*/
        followingGroup.setUserId(userId);
        /**添加分组并返回分组id*/
        Long groupId = userFollowingService.addUserFollowingGroups(followingGroup);
        return new JsonResponse<>(groupId);
    }

    /**
     * <h2>返回当前用户关注列表</h2>
     *
     * @return 返回用户关注列表
     */
    @GetMapping("user-following-groups")
    public JsonResponse<List<FollowingGroup>> getUserFollowingGroups() {
        /**获取当前用户id*/
        Long userId = userSupport.getCurrentUserId();
        /**根据id返回用户关注分类列表*/
        List<FollowingGroup> list = userFollowingService.getUserFollowingGroups(userId);
        /**返回用户关注列表*/
        return new JsonResponse<>(list);
    }

}
