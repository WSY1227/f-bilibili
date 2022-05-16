package com.f.bilibili.domain;

import lombok.Data;

/**
 * @ClassName: JsonResponse
 * @Description: 通用响应对象
 * @author: XU
 * @date: 2022年04月26日 13:51
 **/

/**
 * <h1>通用响应对象</h1>
 **/
@Data
public class JsonResponse<T> {
    /**
     * 状态码
     */
    private String code;
    /**
     * 消息
     */
    private String massage;
    /**
     * 数据
     */
    private T data;

    public JsonResponse(String code, String massage) {
        this.code = code;
        this.massage = massage;
    }

    public JsonResponse(T data) {
        this.data = data;
        this.massage = "成功";
        this.code = "0";
    }

    /**
     * <h2>返回一个没有data的成功信息</h2>
     */
    public static JsonResponse<String> success() {
        return new JsonResponse<>(null);
    }

    /**
     * <h2>返回一个添加data的成功信息</h2>
     */
    public static JsonResponse<String> success(String data) {
        return new JsonResponse<>(data);
    }

    /**
     * <h2>返回一个code为1的失败信息</h2>
     */
    public static JsonResponse<String> fail() {
        return new JsonResponse<>("1", "失败");
    }

    /**
     * <h2>返回一个自定义code和message的失败信息</h2>
     */
    public static JsonResponse<String> fail(String code, String msg) {
        return new JsonResponse<>(code, msg);
    }

}
