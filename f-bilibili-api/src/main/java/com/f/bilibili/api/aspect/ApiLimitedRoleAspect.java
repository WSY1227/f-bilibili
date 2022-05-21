package com.f.bilibili.api.aspect;

import com.f.bilibili.api.support.UserSupport;
import com.f.bilibili.domain.annotation.ApiLimitedRole;
import com.f.bilibili.domain.auth.UserRole;
import com.f.bilibili.domain.exception.ConditionException;
import com.f.bilibili.service.UserRoleService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName: ApiLimitedRoleAspect
 * @Description: 接口切面
 * @author: XU
 * @date: 2022年05月18日 16:12
 **/
@Order(1)
@Component
@Aspect
public class ApiLimitedRoleAspect {
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.f.bilibili.domain.annotation.ApiLimitedRole)")
    public void check() {
    }

    /**
     * 切入节点后需要做的
     * 进行角色接口访问权限限制
     */
    @Before("check() && @annotation(apiLimitedRole)")
    public void doBefore(JoinPoint joinPoint, ApiLimitedRole apiLimitedRole) {
        /**获取当前用户id*/
        Long userId = userSupport.getCurrentUserId();
        /**获取用户关联角色列表*/
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        /**获取不能访问的当前接口的角色代码列表*/
        String[] limitedRoleCodeList = apiLimitedRole.limitedRoleCodeList();
        /**将需要限制的权限代码转化为set集合*/
        Set<String> limitedRoleCodeSet = Arrays.stream(limitedRoleCodeList).collect(Collectors.toSet());
        /**关联当前用户角色代码*/
        Set<String> roleCodeSet = userRoleList.stream().map(UserRole::getCode).collect(Collectors.toSet());
        /**取交集*/
        roleCodeSet.retainAll(limitedRoleCodeSet);
        /**如果用户角色代码与被限制的角色代码有交集则不能访问*/
        if (roleCodeSet.size() > 0) {
            throw new ConditionException("权限不足");
        }
    }
}
