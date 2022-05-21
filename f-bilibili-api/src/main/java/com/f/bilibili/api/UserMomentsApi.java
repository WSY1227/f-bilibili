package com.f.bilibili.api;

import com.f.bilibili.api.support.UserSupport;
import com.f.bilibili.domain.JsonResponse;
import com.f.bilibili.domain.UserMoments;
import com.f.bilibili.domain.annotation.ApiLimitedRole;
import com.f.bilibili.domain.annotation.DataLimited;
import com.f.bilibili.domain.constant.AuthRoleConstant;
import com.f.bilibili.service.UserMomentsService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: UserMomentsApi
 * @Description: 消息推送服务
 * @author: XU
 * @date: 2022年05月06日 13:27
 **/
@RestController
public class UserMomentsApi {
    @Autowired
    private UserMomentsService userMomentsService;
    @Autowired
    private UserSupport userSupport;

    /**
     * 新增用户动态
     */
    @ApiLimitedRole(limitedRoleCodeList = {AuthRoleConstant.ROLE_LV0})//代表角色代码为lv0的用户不能访问
    @DataLimited
    @PostMapping("/user-moments")
    public JsonResponse<String> addUserMoments(@RequestBody UserMoments userMoments) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        /**获取当前用户id*/
        Long userId = userSupport.getCurrentUserId();
        /**添加当前用户id*/
        userMoments.setUserId(userId);
        /**新增动态*/
        userMomentsService.addUserMoments(userMoments);
        return JsonResponse.success();

    }

    /**
     * 获当前用户取订阅的动态信息
     */
    @GetMapping("/user-subscribed-moments")
    public JsonResponse<List<UserMoments>> getUserSubscribedMoments() {
        /**获取当前用户id*/
        Long userId = userSupport.getCurrentUserId();
        /**传入当前用户id获取订阅的动态*/
        List<UserMoments> list = userMomentsService.getUserSubscribedMoments(userId);
        /**返回用户动态*/
        return new JsonResponse<>(list);


    }

}
