package com.f.bilibili.domain.auth;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: UserAuthorities
 * @Description: 权限信息集中类
 * @author: XU
 * @date: 2022年05月16日 23:44
 **/
@Data
public class UserAuthorities {
    /**
     * 色与元素操作关联表
     */
    List<AuthRoleElementOperation> roleElementOperationList;
    /**
     * 角色页面菜单关联表
     */
    List<AuthRoleMenu> roleMenuList;
}
