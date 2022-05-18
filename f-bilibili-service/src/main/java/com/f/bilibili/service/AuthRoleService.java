package com.f.bilibili.service;

import com.f.bilibili.domain.auth.AuthRoleElementOperation;
import com.f.bilibili.domain.auth.AuthRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: AuthRoleService
 * @Description: 用户权限服务
 * @author: XU
 * @date: 2022年05月17日 12:29
 **/
@Service
public class AuthRoleService {
    @Autowired
    private AuthRoleElementOperationService authRoleElementOperationService;
    @Autowired
    private AuthRoleMenuService authRoleMenuService;

    /**
     * 通过角色id集合获取操作权限列表
     */
    public List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(Set<Long> roleIdSet) {
        return authRoleElementOperationService.getRoleElementOperationsByRoleIds(roleIdSet);
    }

    /**
     * 通过角色id集合获取页面权限列表
     */
    public List<AuthRoleMenu> getRoleMenuList(Set<Long> roleIdSet) {
        return authRoleMenuService.getRoleMenuList(roleIdSet);
    }
}
