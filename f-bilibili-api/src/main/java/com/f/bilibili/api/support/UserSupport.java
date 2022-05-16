package com.f.bilibili.api.support;

import com.f.bilibili.domain.exception.ConditionException;
import com.f.bilibili.service.util.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @ClassName: UserSupport
 * @Description: 用户支持
 * @author: XU
 * @date: 2022年04月27日 16:46
 **/
@Component
public class UserSupport {
    /**
     * <h2>获取当前用户id</h2>
     *
     * @return 返回当前用户id
     */
    public Long getCurrentUserId() {
        /**拿到请求上下文*/
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        /**拿到请求头携带的token*/
        String token = requestAttributes.getRequest().getHeader("token");
        /**解密token并获取用户id*/
        Long userId = TokenUtil.verifyToken(token);
        if (userId < 0) {
            throw new ConditionException("非法用户");
        }
        return userId;
    }
}
