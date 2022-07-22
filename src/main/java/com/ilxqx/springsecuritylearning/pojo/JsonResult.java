package com.ilxqx.springsecuritylearning.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 响应结果类
 *
 * @author venus
 * @version 1.0.0
 * @since 2022/6/21 22:47
 */
@Data
@AllArgsConstructor(staticName = "create")
public class JsonResult<T> {

    /**
     * 响应码，0表示成功，非0表示失败
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;

    public static <T> JsonResult<T> success(String message, T data) {
        return create(0, message, data);
    }

    public static <T> JsonResult<T> success(String message) {
        return create(0, message, null);
    }

    public static <T> JsonResult<T> failure(String message) {
        return create(1, message, null);
    }
}
