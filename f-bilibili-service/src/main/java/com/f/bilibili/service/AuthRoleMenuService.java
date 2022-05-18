package com.f.bilibili.service;

import com.f.bilibili.dao.AuthRoleMenuDao;
import com.f.bilibili.domain.auth.AuthRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: AuthRoleMenuService
 * @Description: 角色页面访问权限服务
 * @author: XU
 * @date: 2022年05月17日 14:24
 **/
@Service
public class AuthRoleMenuService {
    @Autowired
    private AuthRoleMenuDao authRoleMenuDao;

    /**
     * 通过角色id集合获取页面权限列表
     */
    public List<AuthRoleMenu> getRoleMenuList(Set<Long> roleIdSet) {
        return authRoleMenuDao.getRoleMenuList(roleIdSet);
    }
}
