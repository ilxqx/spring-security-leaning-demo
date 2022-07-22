package com.ilxqx.springsecuritylearning.handler;

import com.ilxqx.springsecuritylearning.pojo.JsonResult;
import com.ilxqx.springsecuritylearning.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证入口
 *
 * @author venus
 * @version 1.0.0
 * @since 2022/6/21 23:37
 */
@Slf4j
@Component
public class FrameworkAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * Commences an authentication scheme.
     * <p>
     * <code>ExceptionTranslationFilter</code> will populate the <code>HttpSession</code>
     * attribute named
     * <code>AbstractAuthenticationProcessingFilter.SPRING_SECURITY_SAVED_REQUEST_KEY</code>
     * with the requested target URL before calling this method.
     * <p>
     * Implementations should modify the headers on the <code>ServletResponse</code> as
     * necessary to commence the authentication process.
     *
     * @param request       that resulted in an <code>AuthenticationException</code>
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.warn("需要认证：{}", authException.getMessage());

        ResponseUtils.responseJson(response, JsonResult.failure("请登录"));
    }
}
