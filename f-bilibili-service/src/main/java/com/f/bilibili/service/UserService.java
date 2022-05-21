package com.f.bilibili.service;

import com.alibaba.fastjson.JSONObject;
import com.f.bilibili.dao.UserDao;
import com.f.bilibili.dao.UserInfoDao;
import com.f.bilibili.domain.PageResult;
import com.f.bilibili.domain.User;
import com.f.bilibili.domain.UserInfo;
import com.f.bilibili.domain.constant.UserConstant;
import com.f.bilibili.domain.exception.ConditionException;
import com.f.bilibili.service.util.MD5Util;
import com.f.bilibili.service.util.RSAUtil;
import com.f.bilibili.service.util.TokenUtil;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: UserService
 * @Description: 用户服务
 * @author: XU
 * @date: 2022年04月26日 19:34
 **/
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserAuthService userAuthService;

    public void addUser(User user) {
        String phone = user.getPhone();
        /**判断用户手机号是否为空，为空返回错误信息*/
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("手机号不能为空");
        }
        /**查询手机号是否注册，查询到用户则已经被注册，返回错误信息*/
        User dbUser = this.getUserByPhone(phone);
        if (dbUser != null) {
            throw new ConditionException("该手机号已经注册");
        }
        Date now = new Date();
        /**将当前时间当作md5的盐值*/
        String salt = String.valueOf(now.getTime());
        String password = user.getPassword();
        String rawPassword;
        try {
            /**解密前端拿到的密码*/
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败");
        }
        /**对解密得到的原密码进行MD5加密*/
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        /**将经过MD5加密后的密码以及盐值、创建时间存入User中*/
        user.setPassword(md5Password);
        user.setSalt(salt);
        user.setCreateTime(now);
        userDao.addUser(user);
        /**添加用户信息*/
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setGender(UserConstant.GENDER_MALE);
        userInfo.setCreateTime(now);
        userInfoDao.addUserInfo(userInfo);
        /**添加用户默认角色*/
        userAuthService.addUserDefaultRole(user.getId());
    }

    public User getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }

    /**
     * <h2>用户登录服务</h2>
     *
     * @param user 用户登录信息
     * @return 登录成功将返对应生产的token信息
     * @throws Exception
     */
    public String login(User user) throws Exception {
        String phone = user.getPhone();
        /**判断用户手机号是否为空，为空返回错误信息*/
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("手机号不能为空");
        }
        /**查询用户是否存在，不存在则返回错误信息*/
        User dbUser = this.getUserByPhone(phone);
        if (dbUser == null) {
            throw new ConditionException("该用户不存在");
        }
        String password = user.getPassword();
        String rawPassword;
        try {
            /**解密前端拿到的密码*/
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("解密失败");
        }
        /**从数据库中拿到盐值与解密得到的原密码进行md5加密然后比对密码*/
        String salt = dbUser.getSalt();
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        if (!md5Password.equals(dbUser.getPassword())) {
            throw new ConditionException("密码错误");
        }
        /**传入用户id生成token返回*/
        return new TokenUtil().generateToken(dbUser.getId());
    }

    /**
     * 查询是否有当前用户信息
     */
    public User getUserInfo(Long userId) {
        /**根据id获取用户*/
        User user = userDao.getUserById(userId);
        /**获取用户信息*/
        user.setUserInfo(userInfoDao.getUserInfoUserId(userId));
        return user;
    }

    /**
     * 根据userId读取获取User对象
     */
    public User getUserById(Long userId) {
        /**根据id获取用户*/
        User user = userDao.getUserById(userId);
        return user;
    }

    /**
     * 修改用户账号服务
     */
    public void updateUsers(User user) throws Exception {
        Long id = user.getId();
        /**判断当前用户是否存在*/
        User dbUser = userDao.getUserById(id);
        if (dbUser == null) {
            throw new ConditionException("用户不存在！");
        }
        /**判断当前密码不为空或者不为空字符串*/
        if (!StringUtils.isNullOrEmpty(user.getPassword())) {
            /**解密密码*/
            String rawPassword = RSAUtil.decrypt(user.getPassword());
            /**进行md5加密*/
            String md5Password = MD5Util.sign(rawPassword, dbUser.getSalt(), "UTF-8");
            user.setPassword(md5Password);
        }
        user.setUpdateTime(new Date());
        userDao.updateUsers(user);
    }

    /**
     * 修改用户信息服务
     */
    public void updateUserInfos(UserInfo userInfo) {
        /**设置修改时间*/
        userInfo.setUpdateTime(new Date());
        /**调用修改方法*/
        userInfoDao.updateUserInfo(userInfo);
    }

    /**
     * 通过id集合返回每个用户的信息
     */
    public List<UserInfo> getUserInfoByUserIds(Set<Long> userIdList) {
        return userInfoDao.getUserInfoByUserIds(userIdList);
    }

    public PageResult<UserInfo> pageListUserInfos(JSONObject params) {
        /**拿到第几页*/
        Integer no = params.getInteger("no");
        /**拿到每页展示数量*/
        Integer size = params.getInteger("size");
        /**计算数据库需要从哪里开始查询*/
        params.put("start", (no - 1) * size);
        /**查询数量*/
        params.put("limit", size);
        /**查询符合条件的总的用户信息总数*/
        Integer total = userInfoDao.pageCountUserInfos(params);
        /**开始分页查询*/
        List<UserInfo> list = new ArrayList<>();
        /**如果符合条件的总数大于0则开始分页查询*/
        if (total > 0) {
            /**拿到根据查询条件得到的用户信息*/
            list = userInfoDao.pageListUserInfos(params);
        }
        /**返回用户信息列表*/
        return new PageResult<>(total, list);
    }
}
