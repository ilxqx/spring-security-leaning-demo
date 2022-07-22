package com.ilxqx.springsecuritylearning.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilxqx.springsecuritylearning.exception.MyFrameworkException;
import com.ilxqx.springsecuritylearning.pojo.JsonResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 响应工具类
 *
 * @author venus
 * @version 1.0.0
 * @since 2022/6/21 22:46
 */
@Component
public final class ResponseUtils {

    private static ObjectMapper objectMapper;

    public ResponseUtils(ObjectMapper objectMapper) {
        ResponseUtils.objectMapper = objectMapper;
    }

    /**
     * 响应JSON字符串
     *
     * @param response 响应对象
     * @param jsonResult 结果对象
     */
    public static void responseJson(HttpServletResponse response, JsonResult<?> jsonResult) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try {
            response.getWriter().write(
                objectMapper.writeValueAsString(jsonResult)
            );
        } catch (IOException e) {
            throw new MyFrameworkException("序列化JSON出现异常：" + e.getMessage(), e);
        }
    }
}
