package com.ilxqx.springsecuritylearning.handler;

import com.ilxqx.springsecuritylearning.pojo.JsonResult;
import com.ilxqx.springsecuritylearning.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功处理器
 *
 * @author venus
 * @version 1.0.0
 * @since 2022/6/21 22:44
 */
@Slf4j
@Component
public class FrameworkAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     *                       the authentication process.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 其他操作
        log.info("{} 登录成功", authentication.getPrincipal());
        ResponseUtils.responseJson(response, JsonResult.success("登录成功", request.getAttribute("accessToken")));
    }
}
