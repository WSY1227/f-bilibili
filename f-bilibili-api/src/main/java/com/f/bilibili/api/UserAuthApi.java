package com.f.bilibili.api;

import com.f.bilibili.api.support.UserSupport;
import com.f.bilibili.domain.JsonResponse;
import com.f.bilibili.domain.auth.UserAuthorities;
import com.f.bilibili.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: UserAuthApi
 * @Description: 用户权限Api
 * @author: XU
 * @date: 2022年05月16日 21:07
 **/
@RestController
public class UserAuthApi {
    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserAuthService userAuthService;

    /**
     * 获取用户权限
     */
    @GetMapping("/user-authorities")
    public JsonResponse<UserAuthorities> getUserAuthorities() {
        /**获取当前用户id*/
        Long userId = userSupport.getCurrentUserId();
        /**获取用户权限*/
        UserAuthorities userAuthorities = userAuthService.getUserAuthorities(userId);
        /**向前端返回用户权限*/
        return new JsonResponse<>(userAuthorities);
    }

}
