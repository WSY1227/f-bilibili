package com.f.bilibili.service;

import com.f.bilibili.domain.auth.AuthRoleElementOperation;
import com.f.bilibili.domain.auth.AuthRoleMenu;
import com.f.bilibili.domain.auth.UserAuthorities;
import com.f.bilibili.domain.auth.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName: UserAuthService
 * @Description:
 * @author: XU
 * @date: 2022年05月16日 21:11
 **/
@Service
public class UserAuthService {
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private AuthRoleService authRoleService;

    /**
     * 获取用户服务权限
     */
    public UserAuthorities getUserAuthorities(Long userId) {
        /**通过用户id获取该用户关联的角色列表*/
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        /**通过用户角色列表提取角色id集合*/
        Set<Long> roleIdSet = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        /**通过角色id集合获取角色操作权限列表*/
        List<AuthRoleElementOperation> roleElementOperationList = authRoleService.getRoleElementOperationsByRoleIds(roleIdSet);
        /**通过角色id集合获取角色页面访问权限列表*/
        List<AuthRoleMenu> roleMenuList = authRoleService.getRoleMenuList(roleIdSet);

        /**返回用户权限表*/
        UserAuthorities userAuthorities = new UserAuthorities();
        userAuthorities.setRoleElementOperationList(roleElementOperationList);
        userAuthorities.setRoleMenuList(roleMenuList);
        return userAuthorities;
    }
}
