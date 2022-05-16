package com.f.bilibili.domain.constant;

/**
 * @ClassName: UserConstant
 * @Description: 用户常量
 * @author: XU
 * @date: 2022年04月27日 0:42
 **/

/**
 * <h1>用户常量</h1>
 **/
public interface UserConstant {
    /**
     * 性别男
     */
    public static final String GENDER_MALE = "0";
    /**
     * 性别女
     */
    public static final String GENDER_FEMALE = "1";
    public static final String GENDER_UNKNOW = "0";
    /**
     * 默认生日
     */
    public static final String DEFAULT_BIRTH = "1999-10-01";
    /**
     * 默认昵称
     */
    public static final String DEFAULT_NICK = "萌新";
    /**
     * 默认的组值
     */
    public static final String USER_FOLLOWING_GROUP_TYPE_DEFAULT = "2";
    /**
     * 自定义分组属性值
     */
    public static final String USER_FOLLOWING_GROUP_TYPE_USER = "3";

    /**
     * 全部分组名
     */
    public static final String USER_FOLLOWING_GROUP_ALL_NAME = "全部关注";


}
