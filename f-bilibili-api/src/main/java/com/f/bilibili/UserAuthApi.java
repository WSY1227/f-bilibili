package com.f.bilibili;

import com.f.bilibili.api.support.UserSupport;
import com.f.bilibili.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: UserAuthApi
 * @Description: 用户权限Api
 * @author: XU
 * @date: 2022年05月16日 21:07
 **/

public class UserAuthApi {
    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserAuthService userAuthService;

    /**
     * 获取用户权限
     */

}
