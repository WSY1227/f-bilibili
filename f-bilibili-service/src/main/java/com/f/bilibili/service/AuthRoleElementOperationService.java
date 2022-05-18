package com.f.bilibili.service;

import com.f.bilibili.dao.AuthRoleElementOperationDao;
import com.f.bilibili.domain.auth.AuthRoleElementOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: AuthRoleElementOperationService
 * @Description: 角色操作权限服务
 * @author: XU
 * @date: 2022年05月17日 14:22
 **/
@Service
public class AuthRoleElementOperationService {
    @Autowired
    private AuthRoleElementOperationDao authRoleElementOperationDao;

    /**
     * 通过角色id集合获取操作权限列表
     *
     * @param roleIdSet 角色id集合
     * @return 操作权限列表
     */
    public List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(Set<Long> roleIdSet) {
        return authRoleElementOperationDao.getRoleElementOperationsByRoleIds(roleIdSet);
    }
}
