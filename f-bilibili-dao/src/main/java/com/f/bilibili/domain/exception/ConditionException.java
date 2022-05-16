package com.f.bilibili.domain.exception;

/**
 * @ClassName: ConditionException
 * @Description: 异常加强实体类
 * @author: XU
 * @date: 2022年04月26日 15:39
 **/
/**自定义异常加强实体类**/
public class ConditionException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String code;

    public ConditionException(String code, String name) {
        super(name);
    }

    public ConditionException(String name) {
        super(name);
        code = "500";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
