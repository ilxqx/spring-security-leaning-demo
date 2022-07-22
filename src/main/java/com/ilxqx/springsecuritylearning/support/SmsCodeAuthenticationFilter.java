package com.ilxqx.springsecuritylearning.support;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 短信验证码登录过滤器
 *
 * @author venus
 * @version 1.0.0
 * @since 2022/6/21 23:47
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    /**
     * @param defaultFilterProcessesUrl the default value for <tt>filterProcessesUrl</tt>.
     */
    protected SmsCodeAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    /**
     * Performs actual authentication.
     * <p>
     * The implementation should do one of the following:
     * <ol>
     * <li>Return a populated authentication token for the authenticated user, indicating
     * successful authentication</li>
     * <li>Return null, indicating that the authentication process is still in progress.
     * Before returning, the implementation should perform any additional work required to
     * complete the process.</li>
     * <li>Throw an <tt>AuthenticationException</tt> if the authentication process
     * fails</li>
     * </ol>
     *
     * @param request  from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     *                 redirect as part of a multi-stage authentication process (such as OpenID).
     * @return the authenticated user token, or null if authentication is incomplete.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String mobilePhone = request.getParameter("mobilePhone");
        String code = request.getParameter("code");

        SmsCodeAuthenticationToken authenticationToken = new SmsCodeAuthenticationToken(mobilePhone, code);
        authenticationToken.setDetails(this.authenticationDetailsSource.buildDetails(request));

        // 认证
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }
}
