package com.f.bilibili.api;

import com.alibaba.fastjson.JSONObject;
import com.f.bilibili.api.support.UserSupport;
import com.f.bilibili.domain.*;
import com.f.bilibili.service.UserFollowingService;
import com.f.bilibili.service.UserService;
import com.f.bilibili.service.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: UserApi
 * @Description: 用户接口
 * @author: XU
 * @date: 2022年04月26日 19:35
 **/
@RestController
public class UserApi {
    @Autowired
    private UserService userService;
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private UserFollowingService userFollowingService;

    /**
     * <h1>返回当前用户信息</h1>
     */
    @GetMapping("/users")
    public JsonResponse<User> getUserInfo() {
        /**获取当前用户id*/
        Long userId = userSupport.getCurrentUserId();
        /**返回当前用户信息*/
        return new JsonResponse<>(userService.getUserInfo(userId));
    }

    /**
     * 获取AES的公钥以便加密传输
     *
     * @return AES公钥
     */
    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey() {
        String publicKeyStr = RSAUtil.getPublicKeyStr();
        return new JsonResponse<>(publicKeyStr);
    }

    /**
     * 注册用户
     *
     * @param user 用户注册的信息
     * @return 返回注册是否成功
     */
    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody User user) {
        userService.addUser(user);
        return JsonResponse.success();
    }

    /**
     * 用户登录
     *
     * @param user 登录信息
     * @return 登陆成功后返回生成的token值
     */
    @PostMapping("/user-tokens")
    public JsonResponse<String> login(@RequestBody User user) throws Exception {
        String token = userService.login(user);
        return new JsonResponse<>(token);
    }

    /**
     * 修改用户账号信息
     *
     * @param user 修改的信息
     * @return
     * @throws Exception
     */
    @PutMapping("/users")
    public JsonResponse<String> updateUsers(@RequestBody User user) throws Exception {
        /**拿到当前用户id*/
        Long userId = userSupport.getCurrentUserId();
        /**将拿到的id设置到user*/
        user.setId(userId);
        userService.updateUsers(user);
        return JsonResponse.success();
    }

    /**
     * <h2>修改当前用户信息</h2>
     *
     * @param userInfo 需要修改的信息
     * @return
     */
    @PutMapping("/user-infos")
    public JsonResponse<String> updateUserInfos(@RequestBody UserInfo userInfo) {
        /**拿到当前用户id*/
        Long userId = userSupport.getCurrentUserId();
        /**将拿到的id设置到userInfo*/
        userInfo.setUserId(userId);
        /**调用修改服务*/
        userService.updateUserInfos(userInfo);
        return JsonResponse.success();
    }

    /**
     * <h2>分页查询用户信息接口</h2>
     *
     * @param no   当前页码
     * @param size 当前页展示多少数据
     * @param nick 查询条件（可以用来对数据库模糊查询）
     * @return 返回当前页用户信息
     */
    @GetMapping("/user-infos")
    public JsonResponse<PageResult<UserInfo>> pageListUSerInfos(@RequestParam Integer no, @RequestParam Integer size, String nick) {
        /**拿到当前用户id*/
        Long userId = userSupport.getCurrentUserId();
        /**存储分页条件*/
        JSONObject params = new JSONObject();
        params.put("no", no);
        params.put("size", size);
        params.put("nick", nick);
        params.put("userId", userId);
        /**获取分页结果*/
        PageResult<UserInfo> result = userService.pageListUserInfos(params);

        if (result.getTotal() > 0) {
            /**添加互粉状态*/
            List<UserInfo> checkUserInfoList = userFollowingService.checkFollowingStatus(result.getList(), userId);
            result.setList(checkUserInfoList);
        }
        return new JsonResponse<>(result);
    }
    /**创建*/
}
