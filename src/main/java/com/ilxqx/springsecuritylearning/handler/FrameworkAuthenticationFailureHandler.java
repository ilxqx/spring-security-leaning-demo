package com.ilxqx.springsecuritylearning.handler;

import com.ilxqx.springsecuritylearning.pojo.JsonResult;
import com.ilxqx.springsecuritylearning.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 *
 * @author venus
 * @version 1.0.0
 * @since 2022/6/21 23:00
 */
@Slf4j
@Component
public class FrameworkAuthenticationFailureHandler implements AuthenticationFailureHandler {
    /**
     * Called when an authentication attempt fails.
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception the exception which was thrown to reject the authentication
     *                  request.
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.warn("登录失败：{}", exception.getMessage());

        // 其他处理逻辑...
        ResponseUtils.responseJson(response, JsonResult.failure("登录失败：" + exception.getMessage()));
    }
}
