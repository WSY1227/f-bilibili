package com.f.bilibili.api.aspect;

import com.f.bilibili.api.support.UserSupport;
import com.f.bilibili.domain.UserMoments;
import com.f.bilibili.domain.auth.UserRole;
import com.f.bilibili.domain.constant.AuthRoleConstant;
import com.f.bilibili.domain.exception.ConditionException;
import com.f.bilibili.service.UserRoleService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName: DataLimitedAspect
 * @Description: 资源限制
 * @author: XU
 * @date: 2022年05月18日 20:42
 **/
@Order(1)
@Component
@Aspect
public class DataLimitedAspect {
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
     * 进行动态发布类型进行限制
     */
    @Before("check()")
    public void doBefore(JoinPoint joinPoint) {
        /**获取当前用户id*/
        Long userId = userSupport.getCurrentUserId();
        /**获取用户关联角色列表*/
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        /**关联当前用户角色代码*/
        Set<String> roleCodeSet = userRoleList.stream().map(UserRole::getCode).collect(Collectors.toSet());
        /**获取限制参数*/
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof UserMoments) {
                /**将参数类型转化为UserMoments类型*/
                UserMoments userMoments = (UserMoments) arg;
                /**获取当新建资源的type类型*/
                String type = userMoments.getType();
                /**如果角色为lv0且发布的动态属性为0则权限不足*/
                if (roleCodeSet.contains(AuthRoleConstant.ROLE_LV0) && !"0".equals(type)) {
                    throw new ConditionException("参数异常");
                }
            }
        }

    }
}
